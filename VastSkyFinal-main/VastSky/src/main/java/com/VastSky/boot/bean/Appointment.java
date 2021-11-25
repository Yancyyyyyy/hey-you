package com.VastSky.boot.bean;

import lombok.Data;
@Data
public class Appointment {
        private int apNo;
        private String aptime;
        private String place;
        private int reservation;
        private int creatorID;
}
