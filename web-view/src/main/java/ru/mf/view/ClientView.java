package ru.mf.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mf.client.ClientViewDto;
import ru.mf.client.service.ClientViewService;
import ru.mf.user.service.UserService;


@PageTitle("C3po | Clients")
@Route("/clients")
public class ClientView extends VerticalLayout {
    private final ClientViewService<ClientViewDto> clientService;
    private final UserService userService;

    Grid<ClientViewDto> grid = new Grid<>(ClientViewDto.class);
    TextField filterText = new TextField();
    ClientForm clientForm;

    @Autowired
    public ClientView(ClientViewService<ClientViewDto> clientViewService, UserService userService) {
        this.userService = userService;
        this.clientService = clientViewService;

        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        grid.setItems(clientViewService.findAllClients());

        add(
                getToolbar(),
                getContent()
        );

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        clientForm.setClient(null);
        clientForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(clientService.findAllClients(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, clientForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, clientForm);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        clientForm = new ClientForm(userService.findAllUsers());
        clientForm.setWidth("25em");

        clientForm.addListener(ClientForm.SaveEvent.class, this::saveClient);
        clientForm.addListener(ClientForm.DeleteEvent.class, this::deleteClient);
        clientForm.addListener(ClientForm.CloseEvent.class, event -> closeEditor());
    }

    private void saveClient(ClientForm.SaveEvent event) {
        var client = event.getClient();
        clientService.saveClient(client);

        updateList();
        closeEditor();
    }

    private void deleteClient(ClientForm.DeleteEvent event) {
        var client = event.getClient();
        clientService.deleteClient(client);
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add client");
        addContactButton.addClickListener(e -> addClient());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addClient() {
        grid.asSingleSelect().clear();
        editClient(new ClientViewDto());
    }

    private void configureGrid() {
        grid.addClassName("clients-grid");
        grid.setSizeFull();
        grid.setColumns("id", "orgName", "inn", "tenant", "personalAccount", "msisdn", "appUser.lastName", "createdDate");
        grid.getColumns().forEach(c -> c.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editClient(e.getValue()));
    }

    private void editClient(ClientViewDto dto) {
        if (dto == null) {
            closeEditor();
        } else {
            clientForm.setClient(dto);
            clientForm.setVisible(true);
            addClassName("editing");
        }
    }

}
