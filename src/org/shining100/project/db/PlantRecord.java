package org.shining100.project.db;

import java.util.LinkedList;

/**
 * Created by yangguang on 2014/5/27.
 */
public class PlantRecord {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<String> getAliases() {
        return aliases;
    }

    public void setAliases(LinkedList<String> aliases) {
        this.aliases = aliases;
    }

    public LinkedList<String> getCategories() {
        return categories;
    }

    public void setCategories(LinkedList<String> categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLightType() {
        return lightType;
    }

    public void setLightType(int lightType) {
        this.lightType = lightType;
    }

    public String getLightSprAut() {
        return lightSprAut;
    }

    public void setLightSprAut(String lightSprAut) {
        this.lightSprAut = lightSprAut;
    }

    public String getLightSum() {
        return lightSum;
    }

    public void setLightSum(String lightSum) {
        this.lightSum = lightSum;
    }

    public String getLightWin() {
        return lightWin;
    }

    public void setLightWin(String lightWin) {
        this.lightWin = lightWin;
    }

    public int getTemperatureType() {
        return temperatureType;
    }

    public void setTemperatureType(int temperatureType) {
        this.temperatureType = temperatureType;
    }

    public String getTemperatureSum() {
        return temperatureSum;
    }

    public void setTemperatureSum(String temperatureSum) {
        this.temperatureSum = temperatureSum;
    }

    public String getTemperatureWin() {
        return temperatureWin;
    }

    public void setTemperatureWin(String temperatureWin) {
        this.temperatureWin = temperatureWin;
    }

    public String getManureTime() {
        return manureTime;
    }

    public void setManureTime(String manureTime) {
        this.manureTime = manureTime;
    }

    public String getManurePeriod() {
        return manurePeriod;
    }

    public void setManurePeriod(String manurePeriod) {
        this.manurePeriod = manurePeriod;
    }

    public String getManureMethod() {
        return manureMethod;
    }

    public void setManureMethod(String manureMethod) {
        this.manureMethod = manureMethod;
    }

    public int getDormancyProp() {
        return dormancyProp;
    }

    public void setDormancyProp(int dormancyProp) {
        this.dormancyProp = dormancyProp;
    }

    public String getDormancyTime() {
        return dormancyTime;
    }

    public void setDormancyTime(String dormancyTime) {
        this.dormancyTime = dormancyTime;
    }

    public String getDormancyMethod() {
        return dormancyMethod;
    }

    public void setDormancyMethod(String dormancyMethod) {
        this.dormancyMethod = dormancyMethod;
    }

    public String getBreedTime() {
        return breedTime;
    }

    public void setBreedTime(String breedTime) {
        this.breedTime = breedTime;
    }

    public String getBreedMethod() {
        return breedMethod;
    }

    public void setBreedMethod(String breedMethod) {
        this.breedMethod = breedMethod;
    }

    public String getClipTime() {
        return clipTime;
    }

    public void setClipTime(String clipTime) {
        this.clipTime = clipTime;
    }

    public String getClipMethod() {
        return clipMethod;
    }

    public void setClipMethod(String clipMethod) {
        this.clipMethod = clipMethod;
    }

    public String getRepotTime() {
        return repotTime;
    }

    public void setRepotTime(String repotTime) {
        this.repotTime = repotTime;
    }

    public String getRepotMethod() {
        return repotMethod;
    }

    public void setRepotMethod(String repotMethod) {
        this.repotMethod = repotMethod;
    }

    public LinkedList<String> getPictures() {
        return pictures;
    }

    public void setPictures(LinkedList<String> pictures) {
        this.pictures = pictures;
    }

    int id;
    String name;
    LinkedList<String> aliases;
    LinkedList<String> categories;
    String description;
    int lightType;
    String lightSprAut;
    String lightSum;
    String lightWin;
    int temperatureType;
    String temperatureSum;
    String temperatureWin;
    String manureTime;
    String manurePeriod;
    String manureMethod;
    int dormancyProp;
    String dormancyTime;
    String dormancyMethod;
    String breedTime;
    String breedMethod;
    String clipTime;
    String clipMethod;
    String repotTime;
    String repotMethod;
    LinkedList<String> pictures = new LinkedList<String>();
}
