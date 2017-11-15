package ki.oprysko.domain;

import javax.persistence.*;

/**
 * створювати новий контракт (`name`, `surname`, `personal id`, `term` та інші поля (за бажанням))
 *
 * @author Oprysko Svyatoslav
 */
@Entity(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String term;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Contract() {
    }

    public Contract(String term, Country country, User user) {
        this();
        this.term = term;
        this.country = country;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        return id == contract.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
