package com.project.sportsgeek.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Team;
import com.project.sportsgeek.repository.teamrepo.TeamRepository;
import com.project.sportsgeek.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.security.krb5.Credentials;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class TeamService {

    private static String TEMP_URL = "" ;
    @Autowired
    @Qualifier("teamRepo")
    TeamRepository teamRepository;

    public Result<List<Team>> findAllTeam() {
        List<Team> teamList = teamRepository.findAllTeam();
        return new Result<>(200,"Team Details Retrieved Successfully",teamList);
    }

    public Result<Team> findTeamById(int id) throws Exception {
        List<Team> teamList = teamRepository.findTeamById(id);
        if (teamList.size() > 0) {
            return new Result<>(200,"Team Details Retrieved Successfully", teamList.get(0));
        }
        else {
            throw new ResultException((new Result<>(404,"No team's found,please try again","Team with id=('"+ id +"') not found")));
        }
    }

    public Result<Team> addTeam(Team team, MultipartFile multipartFile) throws Exception {
        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.

            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            TEMP_URL = this.uploadFile(file, fileName);                                   // to get uploaded file link
            file.delete();
//            System.out.println("File:"+file);// to delete the copy of uploaded file stored in the project folder// Your customized response
        String teamlogo = "https://firebasestorage.googleapis.com/v0/b/sportsgeek-74e1e.appspot.com/o/" +file+"?alt=media&token=e9924ea4-c2d9-4782-bc2d-0fe734431c86";
//            System.out.println(teamlogo);
        team.setTeamLogo(teamlogo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int id = teamRepository.addTeam(team);
        team.setTeamId(id);
        if (id > 0) {
            return new Result<>(201,"Team Added Successfully",team);
        }
        throw new ResultException(new Result<>(400, "Error!, please try again!", new ArrayList<>(Arrays
                .asList(new Result.SportsGeekSystemError(team.hashCode(), "unable to add the given team")))));
    }
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }
    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("sportsgeek-74e1e.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("D:\\HIVE_PROJECT\\sportsgeek\\sportsgeek-74e1e-firebase-adminsdk-4s62v-7cc67b989e.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format("https://firebasestorage.googleapis.com/v0/b/sportsgeek-74e1e.appspot.com/o/", URLEncoder.encode(fileName, String.valueOf(StandardCharsets.UTF_8)));
    }
    public Result<Team> updateTeam(int id, Team team) throws Exception {
        if (teamRepository.updateTeam(id,team)) {
            return new Result<>(201,"Team Updated Successfully",team);
        }
        throw new ResultException(new Result<>(400, "Unable to update the given team details! Please try again!", new ArrayList<>(Arrays
                .asList(new Result.SportsGeekSystemError(team.hashCode(), "given teamId('"+id+"') does not exists")))));
    }
    public Result<Integer> deleteTeam(int id) throws Exception{
        int data = teamRepository.deleteTeam(id);
        if (data > 0) {
            return new Result<>(200,"Team Deleted Successfully",data);
        }
        else {
            throw new ResultException((new Result<>(404,"No Team's found to delete,please try again","Teams with id=('"+ id +"') not found")));
        }
    }
}
