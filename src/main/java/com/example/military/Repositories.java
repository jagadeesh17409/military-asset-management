package com.example.military;
import org.springframework.data.jpa.repository.JpaRepository;
interface PurchaseRepository extends JpaRepository<Purchase,Long>{}
interface TransferRepository extends JpaRepository<Transfer,Long>{}
interface AssignmentRepository extends JpaRepository<Assignment,Long>{}
interface AuditRepository extends JpaRepository<AuditLog,Long>{}
