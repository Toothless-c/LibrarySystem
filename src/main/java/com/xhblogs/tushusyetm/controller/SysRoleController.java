package com.xhblogs.tushusyetm.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xhblogs.tushusyetm.entity.SysRole;
import com.xhblogs.tushusyetm.parm.AssignParm;
import com.xhblogs.tushusyetm.parm.AssignVo;
import com.xhblogs.tushusyetm.parm.RoleParm;
import com.xhblogs.tushusyetm.parm.SaveAssign;
import com.xhblogs.tushusyetm.service.RoleMenuService;
import com.xhblogs.tushusyetm.service.SysRoleService;
import com.xhblogs.tushusyetm.utlis.ResultUtils;
import com.xhblogs.tushusyetm.utlis.ResultVo;
import com.xhblogs.tushusyetm.utlis.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
@RestController
@RequestMapping("/api/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private RoleMenuService roleMenuService;
    //新增角色
    @PostMapping
    public ResultVo addRole(@RequestBody SysRole role){
        role.setCreateTime(new Date());
        boolean save = sysRoleService.save(role);
        if (save){
            return ResultUtils.success("添加成功", StatusCode.SUCCESS_CODE);
        }else {
            return ResultUtils.success("添加失败",StatusCode.SUCCESS_CODE);
        }
    }
    //编辑角色
    @PutMapping
    public ResultVo editRole(@RequestBody SysRole role){
        role.setUpdateTime(new Date());
        boolean save = sysRoleService.updateById(role);
        if(save){
            return ResultUtils.success("编辑成功",StatusCode.SUCCESS_CODE);
        }
        return ResultUtils.error("编辑失败",StatusCode.SUCCESS_CODE);
    }

    //删除角色
    @DeleteMapping("/{roleId}")
    public ResultVo deleteRole(@PathVariable("roleId") Long roleId){
        boolean b = sysRoleService.removeById(roleId);
        if(b){
            return ResultUtils.success("删除成功",StatusCode.SUCCESS_CODE);
        }
        return ResultUtils.error("删除失败",StatusCode.SUCCESS_CODE);
    }

    //角色列表
    @GetMapping("/list")
    public ResultVo getList(RoleParm parm){
        IPage<SysRole> list = sysRoleService.list(parm);
        return ResultUtils.success("查询成功",StatusCode.SUCCESS_CODE,list);
    }
    //查询角色权限树的回显
    @GetMapping("/getAssingShow")
    public ResultVo getAssingShow(AssignParm parm){
        AssignVo show = sysRoleService.getAssignShow(parm);
        return ResultUtils.success("查询成功",show);
    }
    //角色分配权限保存
    @PostMapping("/assignSave")
        public ResultVo assignSave(@RequestBody SaveAssign parm){
        roleMenuService.assignSave(parm.getRoleId(),parm.getList());
        return ResultUtils.success("分配成功!");
    }
}
