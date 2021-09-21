
package Model;

/**
 *
 * @author rkson
 */
public class ModelTable {
    
    String name, branch, mobile, gender, image;

    public ModelTable(String name, String branch, String mobile, String gender) {
        this.name = name;
        this.branch = branch;
        this.mobile = mobile;
        this.gender = gender;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
}
