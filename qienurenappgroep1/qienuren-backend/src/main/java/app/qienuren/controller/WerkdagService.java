package app.qienuren.controller;

import app.qienuren.exceptions.OverwerkException;
import app.qienuren.model.Werkdag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
    @Transactional
    public class WerkdagService {
    @Autowired
    WerkdagRepository werkdagRepository;

    public Iterable<Werkdag> getAllWorkdays() {
        System.out.println("Je verzoekt alle werkdagen");
        return werkdagRepository.findAll();
    }

    public Object getAllWorkdaysById(long id) {
        try {
            System.out.println("Je verzoekt naar werkdag Id: " + id);
            return werkdagRepository.findById(id).get();
        } catch (Exception e1) {
            System.out.println("Je verzoekt een werkdagId die niet bestaat");
            return "<h1>You Requested User Id That Doesn't Exist</h1>";
        }
    }

    public Werkdag addNewWorkday(Werkdag werkdag) {
        double uren = werkdag.getOpdrachtUren() + werkdag.getOverwerkUren() + werkdag.getTrainingsUren();
        if (uren > 10) {
            throw new OverwerkException("Je mag niet meer dan 10 uur per dag werken");
        } else {
            return werkdagRepository.save(werkdag);
        }
    }

    public void editWerkdag(Werkdag werkdag, long id) {
        Werkdag newWerkdag = werkdagRepository.findById(id).get();
        newWerkdag.setOpdrachtUren(werkdag.getOpdrachtUren());
        newWerkdag.setOverwerkUren(werkdag.getOverwerkUren());
        newWerkdag.setVerlofUren(werkdag.getVerlofUren());
        newWerkdag.setZiekteDag(werkdag.getZiekteDag());
        newWerkdag.setTrainingsUren(werkdag.getTrainingsUren());
        newWerkdag.setOverigeUren(werkdag.getOverigeUren());
        newWerkdag.setVerklaring(werkdag.getVerklaring());
        werkdagRepository.save(newWerkdag);
    }

//        public Werkdag addNewHours(long hours, long dayId) {
//           wr.findById(dayId).get().setUren(hours);
//           return wr.save(wr.findById(dayId).get());
//        }

}