package com.rab3tech.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.rab3tech.controller.dto.ProfileDTO;
import com.rab3tech.dao.ProfileDao;
import com.rab3tech.utils.Utils;

@Controller
public class CustomerController {

	@Autowired
	private ProfileDao profileDao;

	// <form action="signup" method="post">
	@PostMapping("/signup")
	public String signupPost(HttpServletRequest req) {

		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String qualification = req.getParameter("qualification");
		String mobile = req.getParameter("mobile");
		String gender = req.getParameter("gender");
		String photo = req.getParameter("photo");
		String password = Utils.generateRandomPassword(5);
		String username = email;
		// ProfileDao profileDao=new ProfileDaoImpl();
		ProfileDTO profileDTO = new ProfileDTO(username, password, name, email, mobile, gender, photo, qualification);
		profileDao.createSignup(profileDTO);
		req.setAttribute("hmmmm", "Hi , " + name + " , you have done signup successfully!!!!!!!!!!!");
		return "login";

	}

	@PostMapping("/auth")
	public String authUser(HttpServletRequest req) {
		String pusername = req.getParameter("username");
		String ppassword = req.getParameter("password");
		ProfileDTO profileDTO = profileDao.authUser(pusername, ppassword);
		if (profileDTO != null) {
			// page->request-session-application
			HttpSession session = req.getSession(true);
			session.setAttribute("userData", profileDTO);
			// adding profileDTO object inside request scope with namemagic
			// req.setAttribute("magic", profileDTO);
			return "dashboard";
		} else { // user is not there
			req.setAttribute("hmmmm", "Sorry , username and password are not correct");
			return "login";
		}
	}

	@GetMapping("/deleteProfile")
	public String deleteProfile(HttpServletRequest req) {
		String pusername = req.getParameter("username");
		profileDao.deleteByUsername(pusername);
		return "profiles";
	}

	@GetMapping("/editProfile")
	public String getEditProfile(HttpServletRequest req) {
		String pusername = req.getParameter("username"); // <a
		ProfileDTO profileDTO = profileDao.findByUsername(pusername);
		req.setAttribute("profileDTO", profileDTO);
		return "esignup";
	}

	@GetMapping("/filterProfile")
	public String doFilterProfile(HttpServletRequest req) {
		String filterText = req.getParameter("filterText");
		List<ProfileDTO> profileDTOs = null;
		if (!"Select".equalsIgnoreCase(filterText)) {
			profileDTOs = profileDao.filterProfiles(filterText);
		} else {
			profileDTOs = profileDao.findAll();
		}
		// adding profileDTO object inside request scope with namemagic
		req.setAttribute("listoptions", profileDao.findAllQualification());
		req.setAttribute("profileDTOs", profileDTOs);
		return "profiles";
	}

	@GetMapping("/loggedUser")
	public String loggedUser(HttpServletRequest req) {
		Set<ProfileDTO> loggedUsers = ProfileDTO.loggedInUser();
		req.setAttribute("profileDTOs", loggedUsers);
		return "loggedUsers";
	}

	@GetMapping("/profiles")
	public String showProfiles(HttpServletRequest req) {
		// I need to fetch whole profiles data from database
		List<ProfileDTO> profileDTOs = profileDao.findAll();
		// adding profileDTO object inside request scope with name magic
		req.setAttribute("profileDTOs", profileDTOs);
		req.setAttribute("listoptions", profileDao.findAllQualification());
		return "profiles";
	}

	@GetMapping("/searchProfile")
	public String searchProfile(HttpServletRequest req) {
		String search = req.getParameter("search");
		List<ProfileDTO> profileDTOs = profileDao.searchProfiles(search);
		// adding profileDTO object inside request scope with namemagic
		req.setAttribute("profileDTOs", profileDTOs);
		req.setAttribute("listoptions", profileDao.findAllQualification());
		return "profiles";
	}

	@GetMapping("/sortProfile")
	public String sortProfile(HttpServletRequest req) {
		// I need to fetch whole profiles data from database
		String sort = req.getParameter("sort");
		List<ProfileDTO> profileDTOs = profileDao.sortProfiles(sort);
		// adding profileDTO object inside request scope with namemagic
		req.setAttribute("profileDTOs", profileDTOs);
		req.setAttribute("listoptions", profileDao.findAllQualification());
		return "profiles";

	}

	@PostMapping("/usignup")
	public String usignup(HttpServletRequest req) {
		String username = req.getParameter("username");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String qualification = req.getParameter("qualification");
		String mobile = req.getParameter("mobile");
		String gender = req.getParameter("gender");
		String photo = req.getParameter("photo");
		ProfileDTO profileDTO = new ProfileDTO(username, "", name, email, mobile, gender, photo, qualification);
		profileDao.updateSignup(profileDTO);
		return "redirect:/profiles";
		// resp.sendRedirect(req.getContextPath()+"/profiles");
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		// This code invalidate the session
		HttpSession session = req.getSession(false);
		if (session != null)
			session.invalidate();
		req.setAttribute("hmmmm", "You have logged out successfully!!");
		return "login";
	}

}
