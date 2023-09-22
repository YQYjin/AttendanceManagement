package com.app.control;

import com.app.dataBase.Evection_infos;
import com.app.dataBase.Leave_infos;
import com.app.mapper.EvectionInfosMapper;
import com.app.mapper.LeaveInfosMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class leaveControl {
    @Autowired
    private EvectionInfosMapper evectionInfosMapper;
    @Autowired
    private LeaveInfosMapper leaveInfosMapper;

    @PostMapping("/leave/submit")
    public String leaveSubmit(@RequestBody Map<String, String> data){
        String userID=data.get("userID");
        String type=data.get("type");
        String reason=data.get("reason");
        String startDate=data.get("start_date");
        String endDate=data.get("end_date");
        //输出报文数据
        System.out.println("请假的用户ID为："+userID);
        System.out.println("请假类型为："+type);
        System.out.println("请假原因为："+reason);
        System.out.println("请假开始时间为："+startDate);
        System.out.println("请假结束时间为："+endDate);

        if(type.equals("病假")||type.equals("事假")) {
            //将请假申请信息插入表中
            Leave_infos info = new Leave_infos();
            info.setWorkerNum(Integer.parseInt(userID));
            info.setType(type);
            info.setReason(reason);
            info.setStartTime(startDate);
            info.setEndTime(endDate);
            leaveInfosMapper.insert(info);
        }else {
            //将出差申请信息插入表中
            Evection_infos info = new Evection_infos();
            info.setWorkerNum(Integer.parseInt(userID));
            info.setReason(reason);
            info.setStartTime(startDate);
            info.setEndTime(endDate);
            evectionInfosMapper.insert(info);
        }
        return "success";
    }
}
