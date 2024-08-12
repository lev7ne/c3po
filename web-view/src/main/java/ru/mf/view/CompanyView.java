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

import java.time.format.DateTimeFormatter;


@PageTitle("C3po | Company")
@Route("/companies")
public class CompanyView extends VerticalLayout {
    private final ClientViewService clientService;
    private final UserService userService;

    Grid<ClientViewDto> grid = new Grid<>(ClientViewDto.class);
    TextField filterText = new TextField();
    CompanyForm companyForm;

    @Autowired
    public CompanyView(ClientViewService clientViewService, UserService userService) {
        this.userService = userService;
        this.clientService = clientViewService;

        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureCompanyForm();

        grid.setItems(clientViewService.findAllClients());

        add(
                getToolbar(),
                getContent()
        );

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        companyForm.setClient(null);
        companyForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(clientService.findAllClients(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, companyForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, companyForm);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureGrid() {
        grid.addClassName("companies-grid");
        grid.setSizeFull();
        grid.setColumns("id", "orgName", "inn", "tenant", "personalAccount", "msisdn", "appUser.lastName", "createdDate");


        grid.getColumnByKey("id").setHeader("ID");
        grid.getColumnByKey("orgName").setHeader("Название организации");
        grid.getColumnByKey("inn").setHeader("ИНН");
        grid.getColumnByKey("tenant").setHeader("Тенант");
        grid.getColumnByKey("personalAccount").setHeader("Лицевой счет");
        grid.getColumnByKey("msisdn").setHeader("MSISDN");
        grid.getColumnByKey("appUser.lastName").setHeader("Менеджер CCC");
        grid.getColumnByKey("createdDate").setHeader("Дата создания");

        grid.getColumns().forEach(c -> c.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editCompany(e.getValue()));
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Быстрый поиск...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Добавить организацию");
        addContactButton.addClickListener(e -> addCompany());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureCompanyForm() {
        companyForm = new CompanyForm(userService.findAllUsers());
        companyForm.setWidth("25em");

        companyForm.addListener(CompanyForm.SaveEvent.class, this::saveCompany);
        companyForm.addListener(CompanyForm.DeleteEvent.class, this::deleteCompany);
        companyForm.addListener(CompanyForm.CloseEvent.class, event -> closeEditor());
    }

    private void saveCompany(CompanyForm.SaveEvent event) {
        clientService.saveClient(event.getClient());
        updateList();
        closeEditor();
    }

    private void editCompany(ClientViewDto dto) {
        if (dto == null) {
            closeEditor();
        } else {
            companyForm.setClient(dto);
            companyForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void deleteCompany(CompanyForm.DeleteEvent event) {
        clientService.deleteClient(event.getClient());
        updateList();
        closeEditor();
    }

    private void addCompany() {
        grid.asSingleSelect().clear();
        companyForm.setClient(new ClientViewDto());
        companyForm.setVisible(true);
        addClassName("editing");
    }
}