package com.campusconnect.controller;

import com.campusconnect.entity.StudentProfile;
import com.campusconnect.entity.User;
import com.campusconnect.service.DirectoryService;
import com.campusconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private UserService userService;

    @GetMapping("/directory")
    public String showStudentDirectory(@RequestParam(value = "query", required = false) String query,
                                        @RequestParam(value = "department", required = false) String department,
                                        @RequestParam(value = "year", required = false) String year,
                                        @AuthenticationPrincipal UserDetails userDetails,
                                        Model model) {

        User currentUser = userDetails != null ? userService.findByEmail(userDetails.getUsername()).orElse(null) : null;
        List<StudentProfile> students = directoryService.searchStudents(query, department, year);

        model.addAttribute("students", students);
        model.addAttribute("query", query);
        model.addAttribute("department", department);
        model.addAttribute("year", year);
        model.addAttribute("currentUser", currentUser);
        return "directory/list";
    }
}
