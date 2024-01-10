package com.app.control;

import com.app.dataBase.*;
import com.app.mapper.EvectionInfosMapper;
import com.app.mapper.LeaveInfosMapper;
import com.app.mapper.MyEvectionInfosMapper;
import com.app.mapper.MyLeaveInfosMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ApplyControl {
    @Autowired
    private MyLeaveInfosMapper myLeaveInfosMapper;
    @Autowired
    private MyEvectionInfosMapper myEvectionInfosMapper;
    @Autowired
    private LeaveInfosMapper leaveInfosMapper;
    @Autowired
    private EvectionInfosMapper evectionInfosMapper;
    @GetMapping("/admin/leave/all")
    public List<LeaveInfoWithName> getAllLeave(){
        List<LeaveInfoWithName> infos=myLeaveInfosMapper.selectAllLeaveWithName();
        return infos;
    }
    @GetMapping("/admin/evection/all")
    public List<EvectionInfosWithName> getAllEvection(){
        List<EvectionInfosWithName> infos=myEvectionInfosMapper.selectAllEvectionWithName();
        return infos;
    }
    @GetMapping("/leave/{userID}")
    public List<LeaveInfoWithName> getLeaveByID(@PathVariable String userID){
        //将userID转为整形
        int id=Integer.parseInt(userID);
        //List<LeaveInfoWithName> infos=myLeaveInfosMapper.selectAllLeaveWithNameByID(id);
        List<LeaveInfoWithName> infos=myLeaveInfosMapper.selectAllLeaveWithNameByIDProduce(id);
        return infos;
    }
    @GetMapping("/evection/{userID}")
    public List<EvectionInfosWithName> getEvectionByID(@PathVariable String userID){
        //将userID转为整形
        int id=Integer.parseInt(userID);
        //List<EvectionInfosWithName> infos=myEvectionInfosMapper.selectAllEvectionWithNameByID(id);
        List<EvectionInfosWithName> infos=myEvectionInfosMapper.selectAllEvectionWithNameByIDProduce(id);
        return infos;
    }
    @PostMapping("/admin/leave/{leaveNum}/{isPass}")
    public String passLeave(@PathVariable String leaveNum,@PathVariable String isPass,@RequestBody Map<String, String> data){
        System.out.println("请假审批:" + leaveNum + " " + isPass);
        String opinion=data.get("opinion");
        System.out.println("审批意见:"+opinion);
        Leave_infos leave_infos=leaveInfosMapper.selectById(Integer.parseInt(leaveNum));
        leave_infos.setIsPass(Byte.parseByte(isPass));
        leave_infos.setOpinion(opinion);
        leaveInfosMapper.updateById(leave_infos);
        return "success";
    }

    @PostMapping("/admin/evection/{evectionNum}/{isPass}")
    public String passEvection(@PathVariable String evectionNum,@PathVariable String isPass,@RequestBody Map<String, String> data){
        System.out.println("出差审批:" + evectionNum + " " + isPass);
        String opinion=data.get("opinion");
        System.out.println("审批意见:"+opinion);
        Evection_infos evectionInfos=evectionInfosMapper.selectById(Integer.parseInt(evectionNum));
        evectionInfos.setIsPass(Byte.parseByte(isPass));
        evectionInfos.setOpinion(opinion);
        evectionInfosMapper.updateById(evectionInfos);
        return "success";
    }
}
