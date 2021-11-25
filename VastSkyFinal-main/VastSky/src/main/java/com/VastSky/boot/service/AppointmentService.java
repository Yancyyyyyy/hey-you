package com.VastSky.boot.service;
import com.VastSky.boot.bean.Appointment;
import com.VastSky.boot.bean.User;
import com.VastSky.boot.mapper.AppointmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    AppointmentMapper appointmentmapper;

    public void addAppointment(Appointment appointment) {appointmentmapper.addAppointment(appointment);}
    public List<Appointment> getAllRoom(){return appointmentmapper.getAllRoom();}
    public List<Appointment> getRoomByPlace(String place){return appointmentmapper.getRoomByPlace(place);}
    public User getUserByIdFromUsers(int id){return appointmentmapper.getUserByIdFromUsers(id);}
    public List<Appointment> getSelfRoom(int id){return appointmentmapper.getSelfRoom(id);}
    public Appointment getIDByapNo(int apNo){return appointmentmapper.getIDByapNo(apNo);}
    public void deleteRoom(int No){appointmentmapper.deleteRoom(No);}

}
