package com.example.military;
import org.springframework.web.bind.annotation.*; import org.springframework.http.*; import java.util.*;
@RestController @RequestMapping("/api")
public class AssetController {
 private final PurchaseRepository purchases; private final TransferRepository transfers; private final AssignmentRepository assignments; private final AuditRepository audits;
 public AssetController(PurchaseRepository p,TransferRepository t,AssignmentRepository a,AuditRepository l){purchases=p;transfers=t;assignments=a;audits=l;}
 private void log(String action,String details){ AuditLog x=new AuditLog(); x.action=action; x.details=details; audits.save(x); }
 @GetMapping("/purchases") public List<Purchase> purchases(){return purchases.findAll();}
 @PostMapping("/purchases") public ResponseEntity<?> addPurchase(@RequestBody Purchase x){ if(x.baseName==null||x.equipmentType==null||x.quantity==null||x.quantity<=0)return ResponseEntity.badRequest().body("Invalid purchase data"); Purchase saved=purchases.save(x); log("PURCHASE","Purchase #"+saved.id); return ResponseEntity.ok(saved); }
 @GetMapping("/transfers") public List<Transfer> transfers(){return transfers.findAll();}
 @PostMapping("/transfers") public ResponseEntity<?> addTransfer(@RequestBody Transfer x){ if(x.fromBase==null||x.toBase==null||x.fromBase.equals(x.toBase)||x.equipmentType==null||x.quantity==null||x.quantity<=0)return ResponseEntity.badRequest().body("Invalid transfer data"); Transfer saved=transfers.save(x); log("TRANSFER","Transfer #"+saved.id); return ResponseEntity.ok(saved); }
 @GetMapping("/assignments") public List<Assignment> assignments(){return assignments.findAll();}
 @PostMapping("/assignments") public ResponseEntity<?> addAssignment(@RequestBody Assignment x){ if(x.baseName==null||x.equipmentType==null||x.personnelName==null||x.quantity==null||x.quantity<=0)return ResponseEntity.badRequest().body("Invalid assignment data"); Assignment saved=assignments.save(x); log("ASSIGNMENT","Assignment #"+saved.id); return ResponseEntity.ok(saved); }
 @PutMapping("/assignments/{id}/expend") public ResponseEntity<?> expend(@PathVariable Long id){ return assignments.findById(id).map(x->{x.expended=true; Assignment saved=assignments.save(x); log("EXPEND","Assignment #"+id); return ResponseEntity.ok(saved);}).orElse(ResponseEntity.notFound().build()); }
 @GetMapping("/audit-logs") public List<AuditLog> logs(){return audits.findAll();}
 @GetMapping("/dashboard") public Map<String,Object> dashboard(){ int purchase=purchases.findAll().stream().mapToInt(x->x.quantity==null?0:x.quantity).sum(); int tin=transfers.findAll().stream().mapToInt(x->x.quantity==null?0:x.quantity).sum(); int tout=tin; int assigned=assignments.findAll().stream().mapToInt(x->x.quantity==null?0:x.quantity).sum(); int expended=assignments.findAll().stream().filter(x->Boolean.TRUE.equals(x.expended)).mapToInt(x->x.quantity==null?0:x.quantity).sum(); Map<String,Object> m=new LinkedHashMap<>(); m.put("openingBalance",0);m.put("purchases",purchase);m.put("transferIn",tin);m.put("transferOut",tout);m.put("netMovement",purchase+tin-tout);m.put("assigned",assigned);m.put("expended",expended);m.put("closingBalance",purchase+tin-tout-assigned); return m; }
}
