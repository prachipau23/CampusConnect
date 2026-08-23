package com.campusconnect.controller;

import com.campusconnect.entity.Resource;
import com.campusconnect.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping
    public ResponseEntity<List<Resource>> list() {
        // Return metadata only — fileData excluded in JSON serialization via @JsonIgnore
        return ResponseEntity.ok(resourceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getById(@PathVariable Long id) {
        return ResponseEntity.ok(resourceService.getById(id));
    }

    /**
     * Returns actual file bytes with proper Content-Disposition header.
     * This is a REAL download, not a placeholder.
     */
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        Resource resource = resourceService.getById(id);
        String contentType = resource.getFileType() != null
                ? resource.getFileType() : MediaType.APPLICATION_OCTET_STREAM_VALUE;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getOriginalFileName() + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .contentLength(resource.getFileSizeBytes())
                .body(resource.getFileData());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> upload(@RequestParam String title,
                                           @RequestParam(required = false) String description,
                                           @RequestParam(required = false) String category,
                                           @RequestParam("file") MultipartFile file,
                                           Authentication auth) throws IOException {
        return ResponseEntity.ok(
                resourceService.upload(title, description, category, file, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        resourceService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Resource deleted"));
    }
}
