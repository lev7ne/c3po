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
import ru.mf.company.CompanyCreateDto;
import ru.mf.company.CompanyMapper;
import ru.mf.company.CompanyUpdateDto;
import ru.mf.company.CompanyViewDto;
import ru.mf.company.service.CompanyService;
import ru.mf.user.service.UserService;


@PageTitle("C3po | Companies")
@Route("/companies")
public class CompanyView extends VerticalLayout {
    private final CompanyService<CompanyCreateDto, CompanyUpdateDto, CompanyViewDto> companyViewService;
    private final UserService userService;
    private final CompanyMapper companyMapper;

    Grid<CompanyViewDto> grid = new Grid<>(CompanyViewDto.class);
    TextField filterText = new TextField();
    CompanyForm companyForm;

    private boolean isCreate = false;

    @Autowired
    public CompanyView(
            CompanyService<CompanyCreateDto, CompanyUpdateDto, CompanyViewDto> companyViewService,
            UserService userService,
            CompanyMapper companyMapper
    ) {

        this.userService = userService;
        this.companyViewService = companyViewService;
        this.companyMapper = companyMapper;

        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureCompanyForm();

        grid.setItems(companyViewService.findAll());

        add(
                getToolbar(),
                getContent()
        );

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        companyForm.setCompany(null);
        companyForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(companyViewService.findAll(filterText.getValue()));
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
        grid.setColumns("id", "orgName", "inn", "appUser.lastName", "createdDate");

//        grid.getColumnByKey("id").setHeader("ID");
//        grid.getColumnByKey("orgName").setHeader("Название организации");
//        grid.getColumnByKey("inn").setHeader("ИНН");
//        grid.getColumnByKey("appUser.lastName").setHeader("Менеджер CCC");
//        grid.getColumnByKey("createdDate").setHeader("Дата создания");

        grid.getColumns().forEach(c -> c.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editCompany(e.getValue()));
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Быстрый поиск...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Создать компанию");
        addContactButton.addClickListener(e -> createCompany());

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
        var viewDto = event.getCompany();

        if (isCreate) {
            var createDto = companyMapper.toCompanyCreateDto(viewDto);
            companyViewService.create(createDto);
        } else {
            var updateDto = companyMapper.toCompanyUpdateDto(viewDto);
            companyViewService.update(updateDto);
        }

        updateList();
        closeEditor();
    }

    private void createCompany() {
        grid.asSingleSelect().clear();
        isCreate = true;
        companyForm.setCompany(new CompanyViewDto());
        companyForm.setVisible(true);
        addClassName("editing");
    }

    private void editCompany(CompanyViewDto dto) {
        if (dto == null) {
            closeEditor();
        } else {
            isCreate = false;
            companyForm.setCompany(dto);
            companyForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void deleteCompany(CompanyForm.DeleteEvent event) {
        companyViewService.delete(event.getCompany());
        updateList();
        closeEditor();
    }

}