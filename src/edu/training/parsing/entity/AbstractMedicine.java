package edu.training.parsing.entity;

import edu.training.parsing.type.MedGroup;

/**
 * Created by Roman on 18.11.2016.
 */
public abstract class AbstractMedicine {
    protected String name;
    protected MedGroup group = MedGroup.NONE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MedGroup getGroup() {
        return group;
    }

    public void setGroup(MedGroup group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return
                "\tName: " + name + '\n' +
                "\tGroup: " + group + '\n';
    }
}
