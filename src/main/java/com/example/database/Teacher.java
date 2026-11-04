package com.example.database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Teacher {

    private int idt;
    private String surname;
    private String name;
    private String patronymic;
    private String phone;
    private String position;
    private LocalDate birthday;
    private String room;

    private Integer curatorGroupId; // допускаем null
    private List<Integer> selectedSubjectIds = new ArrayList<>();
    private List<Integer> selectedGroupIds = new ArrayList<>();

    public Teacher() {}
    public Teacher(int idt, String surname, String name, String patronymic,
                   String phone, String position, LocalDate birthday,
                   String room, Integer curatorGroupId) {
        this.idt = idt;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phone = phone;
        this.position = position;
        this.birthday = birthday;
        this.room = room;
        this.curatorGroupId = curatorGroupId;
    }

    public int getIdt() { return idt; }
    public String getSurname() { return surname; }
    public String getName() { return name; }
    public String getPatronymic() { return patronymic; }
    public String getPhone() { return phone; }
    public String getPosition() { return position; }
    public LocalDate getBirthday() { return birthday; }
    public String getRoom() { return room; }
    public Integer getCuratorGroupId() { return curatorGroupId; }
    public List<Integer> getSelectedSubjectIds() { return selectedSubjectIds; }
    public List<Integer> getSelectedGroupIds() { return selectedGroupIds; }

    public void setIdt(int idt) { this.idt = idt; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setName(String name) { this.name = name; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPosition(String position) { this.position = position; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
    public void setRoom(String room) { this.room = room; }
    public void setCuratorGroupId(Integer curatorGroupId) { this.curatorGroupId = curatorGroupId; }
    public void setSelectedSubjectIds(List<Integer> selectedSubjectIds) { this.selectedSubjectIds = selectedSubjectIds; }
    public void setSelectedGroupIds(List<Integer> selectedGroupIds) { this.selectedGroupIds = selectedGroupIds; }

    public void setCurator(boolean curator) {
        if (!curator) this.curatorGroupId = null;
    }

    public boolean isCurator() {
        return curatorGroupId != null;
    }

    public String getGroupsNames(Map<Integer, Group> groupMap) {
        if (selectedGroupIds == null || selectedGroupIds.isEmpty()) return "";
        return selectedGroupIds.stream()
                .map(groupMap::get)
                .filter(Objects::nonNull)
                .map(Group::getName)
                .collect(Collectors.joining(", "));
    }

    public String getSubjectsNames(Map<Integer, Subject> subjectMap) {
        if (selectedSubjectIds == null || selectedSubjectIds.isEmpty()) return "";
        return selectedSubjectIds.stream()
                .map(subjectMap::get)
                .filter(Objects::nonNull)
                .map(Subject::getName)
                .collect(Collectors.joining(", "));
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (surname != null && !surname.isBlank()) fullName.append(surname);
        if (name != null && !name.isBlank()) fullName.append(" ").append(name);
        if (patronymic != null && !patronymic.isBlank()) fullName.append(" ").append(patronymic);
        return fullName.toString().trim();
    }

    @Override
    public String toString() {
        return surname + " " + name + " " + patronymic;
    }
}
