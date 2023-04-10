package top.xsword.system_service.controller.public_storage;

import org.springframework.web.bind.annotation.*;
import top.xsword.common_service.properties.SystemProperties;
import top.xsword.common_util.exception.FileNotFoundException;
import top.xsword.common_util.exception.TokenParseException;
import top.xsword.common_util.jwt.JwtUtil;
import top.xsword.common_util.result.Result;
import top.xsword.model.entity.File;
import top.xsword.model.entity.User;
import top.xsword.system_service.annotation.ExcludeValidate;
import top.xsword.system_service.service.public_storage.FileService;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Author: ywx
 * Create Time: 2023/2/18
 * Description:
 */
@RestController
@RequestMapping("/download-handler")
public class DownloadController {

    @Resource
    FileService fileService;

    @Resource
    SystemProperties systemProperties;

    @GetMapping
    @ExcludeValidate
    public void generateUrl(HttpServletRequest request, HttpServletResponse response, @RequestParam String token) throws ServletException, IOException {
        Map<String, String> parse = JwtUtil.parse(token, Map.class);
        if (parse == null) {
            throw new TokenParseException();
        }
        String fileId = parse.get("fileId");
        String userId = parse.get("userId");

        //是否有该文件
        File file = fileService.selectOneByDirId(userId, fileId);
        if (file == null) {
            throw new FileNotFoundException();
        }

        String filename = file.getId();
        if (file.getSuffix() != null) {
            filename += ("." + file.getSuffix());
        }
        //拿到文件的路径
        Path path = Paths.get(userId, filename);
        System.out.println(path);

        request.setAttribute("forward", true);
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getFilename(), "utf-8"));
        request.getRequestDispatcher("/download/" + path).forward(request, response);
    }

    @GetMapping("/signature/{fileId}")
    public Result signature(HttpServletRequest request, @PathVariable String fileId) {
        User user = (User) request.getAttribute("user");

        //该用户是否有该文件
        File file = fileService.selectOneByDirId(user.getId(), fileId);
        if (file == null) {
            throw new FileNotFoundException();
        }

        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());
        data.put("fileId", fileId);
        String token = JwtUtil.generateToken(data, TimeUnit.MINUTES, 10);
        String url = systemProperties.getUrl() + "/download-handler?token=" + token;
        return Result.ok(url);
    }
}
