package com.example.ATM_Backend.selectedApp;

import com.example.ATM_Backend.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/selectedapp")
public class SelectedAppController {

    private final SelectedAppService selectedAppService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SelectedAppController(SelectedAppService selectedAppService) {
        this.selectedAppService = selectedAppService;
    }


    @PostMapping("/post")
    public ResponseEntity<String> saveOrUpdateSelectedApp(@Valid @RequestBody SelectedApp selectedApp) {
        String userName = selectedApp.getUserName();
        if (userName == null || userName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("userName cannot be null or empty");
        }
        try {
            selectedAppService.saveOrUpdateSelectedApp(selectedApp);
            return ResponseEntity.ok("SelectedApp saved or updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save or update SelectedApp");
        }
    }

    @GetMapping("/get/{userName}")
    public ResponseEntity<List<SelectedApp>> getSelectedAppByUserName(
            @Parameter(description = "Authorization Token", required = true,
                    examples = @ExampleObject(name = "Authorization 예시", value = "사용자 jwt 토큰"),
                    schema = @Schema(type = "string"))
            @RequestHeader(value = "Authorization") String token,
            @PathVariable("userName") String userName) {

        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            String userPK = jwtTokenProvider.getUserPK(token);
            if (!userName.equals(userPK)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            List<SelectedApp> selectedApps = selectedAppService.getSelectedAppByUserName(userName);
            if (selectedApps.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(selectedApps);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping("/delete/{userName}")
    public ResponseEntity<String> deleteSelectedAppByUserName(
            @Parameter(description = "Authorization Token", required = true,
                    examples = @ExampleObject(name = "Authorization 예시", value = "사용자 jwt 토큰"),
                    schema = @Schema(type = "string"))
            @RequestHeader(value = "Authorization") String token,
            @PathVariable("userName") String userName) {

        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰 형식입니다.");
            }

            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
            }

            String userPK = jwtTokenProvider.getUserPK(token);
            if (!userName.equals(userPK)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 사용자의 userName과 일치하지 않습니다.");
            }

            boolean isDeleted = selectedAppService.deleteSelectedAppByUserName(userName);
            if (isDeleted) {
                return ResponseEntity.ok("SelectedApp deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SelectedApp not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete SelectedApp");
        }
    }

}
