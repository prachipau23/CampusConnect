package com.campusconnect.controller;

import com.campusconnect.entity.*;
import com.campusconnect.service.TeamService;
import com.campusconnect.service.UserService;
import com.campusconnect.service.WorkspaceService;
import com.campusconnect.repository.TeamJoinRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/workspace")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @Autowired
    private TeamJoinRequestRepository joinRequestRepository;

    @GetMapping("/{teamId}")
    public String viewWorkspace(@PathVariable("teamId") Long teamId,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {

        User currentUser = userDetails != null ? userService.findByEmail(userDetails.getUsername()).orElse(null) : null;
        if (currentUser == null) return "redirect:/login";

        Team team = teamService.getTeamById(teamId);
        if (team == null) return "redirect:/teams";

        boolean isMember = workspaceService.isMember(teamId, currentUser);
        if (!isMember) {
            model.addAttribute("team", team);
            model.addAttribute("errorMessage", "Access Restricted: You must be a member of this team workspace.");
            return "redirect:/teams";
        }

        boolean isLeader = team.getCreatedBy().getId().equals(currentUser.getId());

        List<TeamMember> members = workspaceService.getTeamMembers(teamId);
        List<TeamDiscussion> discussions = workspaceService.getDiscussions(teamId);
        List<TeamTask> tasks = workspaceService.getTasks(teamId);
        List<TeamAnnouncement> announcements = workspaceService.getAnnouncements(teamId);
        List<TeamJoinRequest> pendingRequests = isLeader ? joinRequestRepository.findByTeamAndStatus(team, "PENDING") : List.of();

        model.addAttribute("team", team);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isLeader", isLeader);
        model.addAttribute("members", members);
        model.addAttribute("discussions", discussions);
        model.addAttribute("tasks", tasks);
        model.addAttribute("announcements", announcements);
        model.addAttribute("pendingRequests", pendingRequests);

        return "workspace/view";
    }

    @PostMapping("/{teamId}/discussion")
    public String postDiscussion(@PathVariable("teamId") Long teamId,
                                 @RequestParam("content") String content,
                                 @AuthenticationPrincipal UserDetails userDetails) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser != null && workspaceService.isMember(teamId, currentUser)) {
            workspaceService.addDiscussion(teamId, currentUser, content);
        }
        return "redirect:/workspace/" + teamId;
    }

    @PostMapping("/{teamId}/task")
    public String addTask(@PathVariable("teamId") Long teamId,
                          @RequestParam("title") String title,
                          @RequestParam(value = "assignedUserId", required = false) Long assignedUserId,
                          @RequestParam("dueDate") String dueDate,
                          @AuthenticationPrincipal UserDetails userDetails) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser != null && workspaceService.isMember(teamId, currentUser)) {
            workspaceService.addTask(teamId, title, assignedUserId, dueDate);
        }
        return "redirect:/workspace/" + teamId;
    }

    @PostMapping("/task/{taskId}/status")
    public String updateTaskStatus(@PathVariable("taskId") Long taskId,
                                   @RequestParam("status") String status,
                                   @RequestParam("teamId") Long teamId,
                                   @AuthenticationPrincipal UserDetails userDetails) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser != null && workspaceService.isMember(teamId, currentUser)) {
            workspaceService.updateTaskStatus(taskId, status);
        }
        return "redirect:/workspace/" + teamId;
    }

    @PostMapping("/{teamId}/announcement")
    public String addAnnouncement(@PathVariable("teamId") Long teamId,
                                  @RequestParam("title") String title,
                                  @RequestParam("content") String content,
                                  @AuthenticationPrincipal UserDetails userDetails) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (currentUser != null && workspaceService.isMember(teamId, currentUser)) {
            workspaceService.addAnnouncement(teamId, currentUser, title, content);
        }
        return "redirect:/workspace/" + teamId;
    }
}
