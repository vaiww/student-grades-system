package com.example.database;

public class Group {
    private int id;
    private int specId;
    private int collegeId;
    private int courseId;
    private String name;
    private Integer curatorId;
    private String curator;
    private String specName;

    public Group(int id, int specId, int collegeId, int courseId,
                 String name, Integer curatorId, String curator) {

        this.id = id;
        this.specId = specId;
        this.collegeId = collegeId;
        this.courseId = courseId;
        this.name = name;
        this.curatorId = curatorId;
        this.curator = curator;
    }


    @Override
    public String toString() {
        return name;
    }


    public int getId() { return id; }
    public int getSpecId() { return specId; }
    public int getCollegeId() { return collegeId; }
    public int getCourseId() { return courseId; }
    public String getName() { return name; }
    public Integer getCuratorId() { return curatorId; }
    public String getCurator() { return curator; }
    public String getSpecName() { return specName; }


    public void setId(int id) { this.id = id; }
    public void setSpecId(int specId) { this.specId = specId; }
    public void setCollegeId(int collegeId) { this.collegeId = collegeId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public void setName(String name) { this.name = name; }
    public void setCuratorId(Integer curatorId) { this.curatorId = curatorId; }
    public void setCurator(String curator) { this.curator = curator; }
    public void setSpecName(String specName) { this.specName = specName; }
}
