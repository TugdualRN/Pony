package com.pony.entities.models;

import javax.persistence.*;

@Entity
@Table(name = "T_UserCurrencies")
public class UserCurrencies {

    // <editor-fold desc="Fields">
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial", name = "Id")
    private long id;

	@Column(nullable = false)
    private long diamonds;

	@Column(nullable = false)
    private long golds;
    
	@Column(nullable = false)
    private long woods;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    // </editor-fold>

    // <editor-fold desc="Constructors">
    public UserCurrencies() {
        diamonds = 0;
        golds = 500;
        woods = 200;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getter/Setters">
    public long getId() { return this.id; }
    public void setId(long id) { this.id = id; }
    
	public long getDiamonds() { return this.diamonds; }
	public void setDiamonds(long diamonds) { this.diamonds = diamonds; }
    
    public long getGolds() { return this.golds; }
	public void setGolds(long gold) { this.golds = gold; }
    
    public long getWoods() { return this.woods; }
	public void setWoods(long woods) { this.woods = woods; }
    
    public User getUser() { return this.user; }
	public void setUser(User user) { this.user = user; }
    // </editor-fold>
}