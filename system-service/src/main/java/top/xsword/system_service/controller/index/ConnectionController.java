package top.xsword.system_service.controller.index;


import org.springframework.web.bind.annotation.*;

import top.xsword.common_util.result.Result;
import top.xsword.model.entity.Connection;
import top.xsword.model.entity.User;
import top.xsword.system_service.annotation.ExcludeValidate;
import top.xsword.system_service.service.index.ConnectionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ywx
 * @since 2023-01-31
 */
@RestController
@RequestMapping("/connection")
public class ConnectionController {

    @Resource
    ConnectionService connectionService;

    @PostMapping
    public Result createConnection(HttpServletRequest request, @RequestBody Connection connection) {
        User user = (User) request.getAttribute("user");
        //测试是否能连通
        connectionService.testConnection(connection);
        connection.setUserId(user.getId());
        connectionService.save(connection);
        return Result.ok();
    }

    @DeleteMapping("/{storageMode}/{id}")
    public Result deleteConnection(HttpServletRequest request, @PathVariable String id, @PathVariable Integer storageMode) {
        User user = (User) request.getAttribute("user");
        connectionService.removeConnectionByIdAndUserId(id, user.getId(), storageMode);
        return Result.ok();
    }

    @PostMapping("/test")
    @ExcludeValidate
    public Result testConnection(@RequestBody Connection connection) {
        connectionService.testConnection(connection);
        return Result.ok();
    }

    @GetMapping("/{storageMode}")
    public Result getConnection(HttpServletRequest request, @PathVariable Integer storageMode) {
        User user = (User) request.getAttribute("user");
        List<Connection> connections = connectionService.connectionListByUserId(user.getId(), storageMode);
        return Result.ok(connections);
    }
}
