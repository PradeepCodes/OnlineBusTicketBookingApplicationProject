package com.example.OnlineBusTicketBookingApplication.Controller;

import com.example.OnlineBusTicketBookingApplication.DTO.PasswordChangeDTO;
import com.example.OnlineBusTicketBookingApplication.DTO.UserProfileDTO;
import com.example.OnlineBusTicketBookingApplication.Entity.User;
import com.example.OnlineBusTicketBookingApplication.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final  UserService userService;

    @GetMapping
    public String showProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileDTO dto = new UserProfileDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        model.addAttribute("userProfile", dto);
        model.addAttribute("passwordChange", new PasswordChangeDTO());
        model.addAttribute("username", user.getName()); // used in navbar

        return "profile";
    }
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("userProfile") @Valid UserProfileDTO dto,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {
        userService.updateProfile(userDetails.getUsername(), dto);
        model.addAttribute("successMessage", "✅ Profile updated successfully.");
        return "redirect:/user/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute("passwordChange") @Valid PasswordChangeDTO dto,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 Model model) {
        boolean changed = userService.changePassword(userDetails.getUsername(), dto);
        if (changed) {
            model.addAttribute("successMessage", "🔒 Password changed successfully.");
        } else {
            model.addAttribute("errorMessage", "❌ Password change failed. Check credentials.");
        }
        return "redirect:/user/profile";
    }
}
