package com.example.military;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity class Purchase { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; public String baseName,equipmentType; public Integer quantity; public LocalDateTime createdAt=LocalDateTime.now(); }
@Entity class Transfer { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; public String fromBase,toBase,equipmentType; public Integer quantity; public LocalDateTime createdAt=LocalDateTime.now(); }
@Entity class Assignment { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; public String baseName,equipmentType,personnelName; public Integer quantity; public Boolean expended=false; public LocalDateTime createdAt=LocalDateTime.now(); }
@Entity class AuditLog { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id; public String action,details; public LocalDateTime createdAt=LocalDateTime.now(); }
