package ro.magazin.server.service;

import ro.magazin.common.dto.ComandaDTO;
import ro.magazin.common.dto.LinieComandaDTO;
import ro.magazin.common.dto.ProdusDTO;
import ro.magazin.common.dto.UtilizatorDTO;
import ro.magazin.server.model.Comanda;
import ro.magazin.server.model.LinieComanda;
import ro.magazin.server.model.Produs;
import ro.magazin.server.model.Specificatie;
import ro.magazin.server.model.Utilizator;

public final class Mapper {

    private Mapper() {
    }

    /** Hash-ul parolei nu se trimite niciodata clientului. */
    public static UtilizatorDTO toDTO(Utilizator u) {
        UtilizatorDTO dto = new UtilizatorDTO();
        dto.setId(u.getId());
        dto.setNume(u.getNume());
        dto.setEmail(u.getEmail());
        dto.setTelefon(u.getTelefon());
        dto.setRol(u.getRol());
        return dto;
    }

    /** Imaginea (Base64) se completeaza separat, din ImageStorage. */
    public static ProdusDTO toDTO(Produs p) {
        ProdusDTO dto = new ProdusDTO();
        dto.setId(p.getId());
        dto.setDenumire(p.getDenumire());
        dto.setCategorie(p.getCategorie());
        dto.setProducator(p.getProducator());
        dto.setDescriere(p.getDescriere());
        dto.setPret(p.getPret());
        dto.setStoc(p.getStoc());
        for (Specificatie s : p.getSpecificatii()) {
            dto.getSpecificatii().put(s.getNume(), s.getValoare());
        }
        return dto;
    }

    public static ComandaDTO toDTO(Comanda c) {
        ComandaDTO dto = new ComandaDTO();
        dto.setId(c.getId());
        dto.setNumeClient(c.getNumeClient());
        dto.setDataPlasare(c.getDataPlasare());
        dto.setAdresaLivrare(c.getAdresaLivrare());
        dto.setStare(c.getStare());
        dto.setTotal(c.getTotal());
        for (LinieComanda l : c.getLinii()) {
            LinieComandaDTO ldto = new LinieComandaDTO();
            ldto.setIdProdus(l.getProdus().getId());
            ldto.setDenumire(l.getProdus().getDenumire());
            ldto.setCantitate(l.getCantitate());
            ldto.setPretUnitar(l.getPretUnitar());
            dto.getProduse().add(ldto);
        }
        return dto;
    }
}
