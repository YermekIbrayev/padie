package com.iskhak.serviceprovider.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

@AutoValue
public abstract class ServiceGroup {
    public abstract Integer id();
    public abstract String name();
    public abstract String mainQuestion();
    public abstract String extraQuestion();
/*    public abstract List<dbServiceItem> mainSelections();
    public abstract List<dbServiceItem> extraSelections();*/

    public static Builder builder(){
        return new AutoValue_ServiceGroup.Builder();
    }

    public static TypeAdapter<ServiceGroup> typeAdapter(Gson gson){
        return new AutoValue_ServiceGroup.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setId(Integer id);
        public abstract Builder setName(String name);
        public abstract Builder setMainQuestion(String mainQuestion);
        public abstract Builder setExtraQuestion(String extraQuestion);
/*        public abstract Builder setMainSelections(List<dbServiceItem> mainSelections);
        public abstract Builder setExtraSelections(List<dbServiceItem> extraSelections);*/
        public abstract ServiceGroup build();
    }

}
