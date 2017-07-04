package ui.bean;

import MD.Reservas;
import com.facade.ReservasFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "reservasController")
@ViewScoped
public class ReservasController extends AbstractController<Reservas> {

    @Inject
    private CampeonatosController idcampeonatoController;
    @Inject
    private HorariosController idhorarioController;
    @Inject
    private PartidosController idpartidoController;

    // Flags to indicate if child collections are empty
    private boolean isPartidosCollectionEmpty;

    public ReservasController() {
        // Inform the Abstract parent controller of the concrete Reservas Entity
        super(Reservas.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        idcampeonatoController.setSelected(null);
        idhorarioController.setSelected(null);
        idpartidoController.setSelected(null);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsPartidosCollectionEmpty();
    }

    public boolean getIsPartidosCollectionEmpty() {
        return this.isPartidosCollectionEmpty;
    }

    private void setIsPartidosCollectionEmpty() {
        Reservas selected = this.getSelected();
        if (selected != null) {
            ReservasFacade ejbFacade = (ReservasFacade) this.getFacade();
            this.isPartidosCollectionEmpty = ejbFacade.isPartidosCollectionEmpty(selected);
        } else {
            this.isPartidosCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Partidos entities that
     * are retrieved from Reservas?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Partidos page
     */
    public String navigatePartidosCollection() {
        Reservas selected = this.getSelected();
        if (selected != null) {
            ReservasFacade ejbFacade = (ReservasFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Partidos_items", ejbFacade.findPartidosCollection(selected));
        }
        return "/app/partidos/index";
    }

    /**
     * Sets the "selected" attribute of the Campeonatos controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdcampeonato(ActionEvent event) {
        Reservas selected = this.getSelected();
        if (selected != null && idcampeonatoController.getSelected() == null) {
            idcampeonatoController.setSelected(selected.getIdcampeonato());
        }
    }

    /**
     * Sets the "selected" attribute of the Horarios controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdhorario(ActionEvent event) {
        Reservas selected = this.getSelected();
        if (selected != null && idhorarioController.getSelected() == null) {
            idhorarioController.setSelected(selected.getIdhorario());
        }
    }

    /**
     * Sets the "selected" attribute of the Partidos controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdpartido(ActionEvent event) {
        Reservas selected = this.getSelected();
        if (selected != null && idpartidoController.getSelected() == null) {
            idpartidoController.setSelected(selected.getIdpartido());
        }
    }

}
