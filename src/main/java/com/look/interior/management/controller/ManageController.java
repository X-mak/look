package com.look.interior.management.controller;

import com.look.common.Result;
import com.look.interior.management.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    ManageService manageService;

    @PostMapping("/sign/{userAccount}")
    public Result<?> signForTickets(@PathVariable String userAccount){
        int res = manageService.everydaySign(userAccount);
        if(res == -1)
            return Result.error("400","今日已签到!");
        return Result.success("签到成功!");
    }

}
