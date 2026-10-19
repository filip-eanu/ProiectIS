package ro.magazin.server.network;

import ro.magazin.common.dto.AutentificareDTO;
import ro.magazin.common.dto.FiltruProduse;
import ro.magazin.common.dto.OperatieCosDTO;
import ro.magazin.common.dto.ProdusDTO;
import ro.magazin.common.dto.Rol;
import ro.magazin.common.dto.SchimbareStareDTO;
import ro.magazin.common.dto.StareComanda;
import ro.magazin.common.dto.UtilizatorDTO;
import ro.magazin.common.protocol.Request;
import ro.magazin.common.protocol.Response;
import ro.magazin.server.model.Utilizator;
import ro.magazin.server.service.AuthService;
import ro.magazin.server.service.ComandaService;
import ro.magazin.server.service.CosService;
import ro.magazin.server.service.Mapper;
import ro.magazin.server.service.ProdusService;
import ro.magazin.server.service.ServiceException;
import ro.magazin.server.service.StatisticiService;
import ro.magazin.server.service.UtilizatorService;

/**
 * Trimite fiecare cerere catre serviciul potrivit si transforma rezultatul in raspuns.
 */
public class RequestDispatcher {

    private final AuthService authService;
    private final ProdusService produsService;
    private final CosService cosService;
    private final ComandaService comandaService;
    private final UtilizatorService utilizatorService;
    private final StatisticiService statisticiService;

    public RequestDispatcher(AuthService authService, ProdusService produsService, CosService cosService,
                             ComandaService comandaService, UtilizatorService utilizatorService,
                             StatisticiService statisticiService) {
        this.authService = authService;
        this.produsService = produsService;
        this.cosService = cosService;
        this.comandaService = comandaService;
        this.utilizatorService = utilizatorService;
        this.statisticiService = statisticiService;
    }

    public Response proceseaza(Request req, ClientHandler c) {
        if (req.getTip() == null) {
            return Response.eroare("Cerere fara tip");
        }
        try {
            return switch (req.getTip()) {
                case INREGISTRARE -> Response.ok(authService.inregistreaza(req.getPayloadAs(UtilizatorDTO.class)));
                case AUTENTIFICARE -> {
                    AutentificareDTO a = req.getPayloadAs(AutentificareDTO.class);
                    Utilizator u = authService.autentifica(a.getEmail(), a.getParola());
                    c.setUtilizator(u);
                    yield Response.ok(Mapper.toDTO(u));
                }
                case DECONECTARE -> Response.ok();

                case GET_PRODUSE -> {
                    verificaRol(c, null);
                    yield Response.ok(produsService.getProduse());
                }
                case GET_DETALII_PRODUS -> {
                    verificaRol(c, null);
                    yield Response.ok(produsService.getDetalii(req.getPayloadAs(Integer.class)));
                }
                case FILTREAZA_PRODUSE -> {
                    verificaRol(c, null);
                    yield Response.ok(produsService.filtreaza(req.getPayloadAs(FiltruProduse.class)));
                }
                case CAUTA_PRODUSE -> {
                    verificaRol(c, null);
                    yield Response.ok(produsService.cauta(req.getPayloadAs(String.class)));
                }
                case ADAUGA_PRODUS -> {
                    verificaRol(c, Rol.ANGAJAT);
                    yield Response.ok(produsService.adauga(req.getPayloadAs(ProdusDTO.class)));
                }
                case MODIFICA_PRODUS -> {
                    verificaRol(c, Rol.ANGAJAT);
                    yield Response.ok(produsService.modifica(req.getPayloadAs(ProdusDTO.class)));
                }
                case STERGE_PRODUS -> {
                    verificaRol(c, Rol.ANGAJAT);
                    produsService.sterge(req.getPayloadAs(Integer.class));
                    yield Response.ok();
                }

                case GET_COS -> {
                    verificaRol(c, Rol.CLIENT);
                    yield Response.ok(cosService.getCos(idUtilizator(c)));
                }
                case ADAUGA_IN_COS -> {
                    verificaRol(c, Rol.CLIENT);
                    OperatieCosDTO op = req.getPayloadAs(OperatieCosDTO.class);
                    yield Response.ok(cosService.adauga(idUtilizator(c), op.getIdProdus(), op.getCantitate()));
                }
                case MODIFICA_CANTITATE_COS -> {
                    verificaRol(c, Rol.CLIENT);
                    OperatieCosDTO op = req.getPayloadAs(OperatieCosDTO.class);
                    yield Response.ok(cosService.modificaCantitate(idUtilizator(c), op.getIdProdus(), op.getCantitate()));
                }
                case ELIMINA_DIN_COS -> {
                    verificaRol(c, Rol.CLIENT);
                    yield Response.ok(cosService.elimina(idUtilizator(c), req.getPayloadAs(Integer.class)));
                }

                case PLASEAZA_COMANDA -> {
                    verificaRol(c, Rol.CLIENT);
                    yield Response.ok(comandaService.plaseaza(idUtilizator(c), req.getPayloadAs(String.class)));
                }
                case GET_COMENZI_PROPRII -> {
                    verificaRol(c, Rol.CLIENT);
                    yield Response.ok(comandaService.getComenziClient(idUtilizator(c)));
                }
                case GET_TOATE_COMENZILE -> {
                    verificaRol(c, Rol.ANGAJAT);
                    yield Response.ok(comandaService.getToateComenzile());
                }
                case FILTREAZA_COMENZI -> {
                    verificaRol(c, Rol.ANGAJAT);
                    yield Response.ok(comandaService.filtreazaDupaStare(req.getPayloadAs(StareComanda.class)));
                }
                case SCHIMBA_STARE_COMANDA -> {
                    verificaRol(c, Rol.ANGAJAT);
                    SchimbareStareDTO s = req.getPayloadAs(SchimbareStareDTO.class);
                    yield Response.ok(comandaService.schimbaStare(s.getIdComanda(), s.getStare()));
                }

                case GET_UTILIZATORI -> {
                    verificaRol(c, Rol.ANGAJAT);
                    yield Response.ok(utilizatorService.getToti());
                }
                case FILTREAZA_UTILIZATORI -> {
                    verificaRol(c, Rol.ANGAJAT);
                    yield Response.ok(utilizatorService.filtreazaDupaRol(req.getPayloadAs(Rol.class)));
                }
                case ADAUGA_ANGAJAT -> {
                    verificaRol(c, Rol.ANGAJAT);
                    yield Response.ok(utilizatorService.adaugaAngajat(req.getPayloadAs(UtilizatorDTO.class)));
                }
                case MODIFICA_ANGAJAT -> {
                    verificaRol(c, Rol.ANGAJAT);
                    yield Response.ok(utilizatorService.modificaAngajat(req.getPayloadAs(UtilizatorDTO.class)));
                }
                case STERGE_ANGAJAT -> {
                    verificaRol(c, Rol.ANGAJAT);
                    utilizatorService.stergeAngajat(req.getPayloadAs(Integer.class), idUtilizator(c));
                    yield Response.ok();
                }

                case GET_STATISTICI -> {
                    verificaRol(c, Rol.ANGAJAT);
                    yield Response.ok(statisticiService.getStatistici());
                }
            };
        } catch (ServiceException e) {
            return Response.eroare(e.getMessage());
        } catch (UnsupportedOperationException e) {
            return Response.eroare("Functionalitate neimplementata inca: " + req.getTip());
        } catch (RuntimeException e) {
            System.err.println("Eroare la procesarea cererii " + req.getTip() + ": " + e);
            return Response.eroare("Eroare interna a serverului");
        }
    }

    /**
     * @param rol rolul cerut; null inseamna orice utilizator autentificat
     */
    private void verificaRol(ClientHandler c, Rol rol) {
        Utilizator u = c.getUtilizator();
        if (u == null) {
            throw new ServiceException("Trebuie sa va autentificati");
        }
        if (rol != null && u.getRol() != rol) {
            throw new ServiceException("Acces interzis");
        }
    }

    private int idUtilizator(ClientHandler c) {
        return c.getUtilizator().getId();
    }
}
