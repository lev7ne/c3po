package ru.mf.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import ru.mf.company.CompanyViewDto;
import ru.mf.user.UserDto;

import java.util.List;


public class CompanyForm extends FormLayout {
    private Binder<CompanyViewDto> binder = new BeanValidationBinder<>(CompanyViewDto.class);
    private TextField orgName = new TextField("Название");
    private TextField inn = new TextField("ИНН");
    private ComboBox<UserDto> appUsers = new ComboBox<>("Менеджер CCC");

    private final Button save = new Button("Сохранить");
    private final Button delete = new Button("Удалить");
    private final Button cancel = new Button("Отменить");

    private CompanyViewDto dto;

    public CompanyForm(List<UserDto> userDtos) {
        addClassName("company-form");

        binder.bindInstanceFields(this);

        appUsers.setItems(userDtos);
        appUsers.setItemLabelGenerator(UserDto::getLastName);

        binder.forField(appUsers)
                .bind(CompanyViewDto::getAppUser, CompanyViewDto::setAppUser);

        add(orgName, inn, appUsers, createButtonLayout());
    }

    public void setCompany(CompanyViewDto dto) {
        this.dto = dto;
        binder.readBean(dto);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, dto)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        delete.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(dto);
            if (appUsers.getValue() != null) {
                dto.setAppUser(appUsers.getValue());
            }
            fireEvent(new SaveEvent(this, dto));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static class SaveEvent extends ComponentEvent<CompanyForm> {
        private CompanyViewDto dto;

        SaveEvent(CompanyForm source, CompanyViewDto dto) {
            super(source, false);
            this.dto = dto;
        }

        public CompanyViewDto getCompany() {
            return dto;
        }
    }

    public static class DeleteEvent extends ComponentEvent<CompanyForm> {
        private CompanyViewDto dto;

        DeleteEvent(CompanyForm source, CompanyViewDto dto) {
            super(source, false);
            this.dto = dto;
        }

        public CompanyViewDto getCompany() {
            return dto;
        }
    }

    public static class CloseEvent extends ComponentEvent<CompanyForm> {
        CloseEvent(CompanyForm source) {
            super(source, false);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
