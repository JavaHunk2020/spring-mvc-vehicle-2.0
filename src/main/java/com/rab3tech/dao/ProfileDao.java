package com.rab3tech.dao;

import java.util.List;

import com.rab3tech.controller.dto.ProfileDTO;

public interface ProfileDao {

	void show();

	String updateSignup(ProfileDTO profileDTO);

	String createSignup(ProfileDTO profileDTO);

	List<ProfileDTO> sortProfiles(String sort);

	List<String> findAllQualification();

	List<ProfileDTO> filterProfiles(String filterText);

	List<ProfileDTO> searchProfiles(String search);

	List<ProfileDTO> findAll();

	ProfileDTO authUser(String pusername, String ppassword);

	ProfileDTO findByEmail(String pemail);

	ProfileDTO findByUsername(String pusername);

	void deleteByUsername(String pusername);

}
