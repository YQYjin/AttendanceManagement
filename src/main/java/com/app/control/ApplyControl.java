package com.app.control;

import com.app.dataBase.Attendances;
import com.app.dataBase.EvectionInfosWithName;
import com.app.dataBase.LeaveInfoWithName;
import com.app.dataBase.Leave_infos;
import com.app.mapper.LeaveInfosMapper;
import com.app.mapper.MyEvectionInfosMapper;
import com.app.mapper.MyLeaveInfosMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class ApplyControl {
    @Autowired
    private MyLeaveInfosMapper myLeaveInfosMapper;
    @Autowired
    private MyEvectionInfosMapper myEvectionInfosMapper;
    @Autowired
    private LeaveInfosMapper leaveInfosMapper;
    @GetMapping("/leave/all")
    public List<LeaveInfoWithName> getAllLeave(){
        List<LeaveInfoWithName> infos=myLeaveInfosMapper.selectAllLeaveWithName();
        return infos;
    }
    @GetMapping("/evection/all")
    public List<EvectionInfosWithName> getAllEvection(){
        List<EvectionInfosWithName> infos=myEvectionInfosMapper.selectAllEvectionWithName();
        return infos;
    }
    @GetMapping("/leave/{userID}")
    public List<LeaveInfoWithName> getLeaveByID(@PathVariable String userID){
        //将userID转为整形
        int id=Integer.parseInt(userID);
        List<LeaveInfoWithName> infos=myLeaveInfosMapper.selectAllLeaveWithNameByID(id);
        return infos;
    }
    @GetMapping("/evection/{userID}")
    public List<EvectionInfosWithName> getEvectionByID(@PathVariable String userID){
        //将userID转为整形
        int id=Integer.parseInt(userID);
        List<EvectionInfosWithName> infos=myEvectionInfosMapper.selectAllEvectionWithNameByID(id);
        return infos;
    }
    @PostMapping("/leave/{leaveNum}/{isPass}")
    public String passLeave(@PathVariable String leaveNum,@PathVariable String isPass){
        System.out.println("请假审批:" + leaveNum + " " + isPass);
        Leave_infos leave_infos=leaveInfosMapper.selectById(Integer.parseInt(leaveNum));
        leave_infos.setIsPass(Byte.parseByte(isPass));
        leaveInfosMapper.updateById(leave_infos);
        return "success";
    }

    @PostMapping("/evection/{evectionNum}/{isPass}")
    public String passEvection(@PathVariable String evectionNum,@PathVariable String isPass){
        System.out.println("出差审批:" + evectionNum + " " + isPass);
        Leave_infos leave_infos=leaveInfosMapper.selectById(Integer.parseInt(evectionNum));
        leave_infos.setIsPass(Byte.parseByte(isPass));
        leaveInfosMapper.updateById(leave_infos);
        return "success";
    }
}
