package com.VastSky.boot.mapper;

import com.VastSky.boot.bean.Appointment;

import com.VastSky.boot.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface AppointmentMapper {
    public void addAppointment(Appointment appointment);

    public List<Appointment> getAllRoom();

    public User getUserByIdFromUsers(int id);

    public List<Appointment> getRoomByPlace(String place);

    public List<Appointment> getSelfRoom(int id);

    public Appointment getIDByapNo(int apNo);

    public void deleteRoom(int No);

}
