package edu.training.parsing.entity;

import edu.training.parsing.type.MedView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 18.11.2016.
 */
public class Version {
    private List<Pharm> producers = new ArrayList<>();
    private MedView view = MedView.NONE;

    public void setView(MedView view) {
        this.view = view;
    }

    public MedView getView() {
        return view;
    }

    public boolean add(Pharm pharm) {
        return producers.add(pharm);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("\t\t");
        producers.stream().forEach(s -> sb.append(s.toString() + "\n\t\t"));
        return "Version: { View: " + view + "}\n" +
                sb.toString() + "\n\t\t";
    }
}
