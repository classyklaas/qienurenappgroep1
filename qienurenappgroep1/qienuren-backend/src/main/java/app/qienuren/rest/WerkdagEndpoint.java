package app.qienuren.rest;

import app.qienuren.controller.WerkdagService;
import app.qienuren.model.Werkdag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/werkdag")
public class WerkdagEndpoint {
    @Autowired
    WerkdagService werkdagService;

    @PreAuthorize("hasAnyRole('ADMIN')or #id == principal.userId")
    @GetMapping("/werkdagen")
    public Iterable<Werkdag> werkdagList() {
        return werkdagService.getAllWorkdays();
    }

    @PreAuthorize("hasAnyRole('ADMIN')or #id == principal.userId")
    @GetMapping("/{id}")
    public Object getWerkdagById(@PathVariable(value="id")long id) {
        return werkdagService.getAllWorkdaysById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')or #id == principal.userId")
    @PostMapping("/new")
    public Object addNewWerkdag(@RequestBody Werkdag werkdag) {
        try {
            return werkdagService.addNewWorkday(werkdag);
        } catch (Exception e) {
            return e.getMessage();
        }

    }
}
