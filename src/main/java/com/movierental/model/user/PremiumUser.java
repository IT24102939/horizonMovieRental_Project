package com.movierental.model.user;

import java.util.Date;

/**
 * PremiumUser class representing a premium user with extended privileges
 */
public class PremiumUser extends User {
    private static final int RENTAL_LIMIT = 10;
    private Date subscriptionExpiryDate;


    public PremiumUser(String userId, String username, String password, String email, String fullName, Date subscriptionExpiryDate) {
        super(userId, username, password, email, fullName);
        this.subscriptionExpiryDate = subscriptionExpiryDate;
    }


    public PremiumUser(String userId, String username, String password, String email, String fullName) {
        super(userId, username, password, email, fullName);
        // Set default expiry date to one month from now
        this.subscriptionExpiryDate = new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000);
    }


    public PremiumUser() {
        super();
        this.subscriptionExpiryDate = new Date();
    }

    public Date getSubscriptionExpiryDate() {
        return subscriptionExpiryDate;
    }

    public void setSubscriptionExpiryDate(Date subscriptionExpiryDate) {
        this.subscriptionExpiryDate = subscriptionExpiryDate;
    }


    public boolean isSubscriptionActive() {
        return subscriptionExpiryDate.after(new Date());
    }

    @Override
    public int getRentalLimit() {
        return RENTAL_LIMIT;
    }


    public double calculateRentalFee(int daysRented) {
        return 1.99 * daysRented;
    }


    public double calculateLateFee(int daysLate) {
        return 0.75 * daysLate;
    }

    @Override
    public String toFileString() {
        return "PREMIUM," + super.toFileString() + "," + subscriptionExpiryDate.getTime();
    }


    public static PremiumUser fromFileString(String fileString) {
        String[] parts = fileString.split(",");
        if (parts.length >= 7 && parts[0].equals("PREMIUM")) {
            PremiumUser user = new PremiumUser(parts[1], parts[2], parts[3], parts[4], parts[5]);
            try {
                long expiryTime = Long.parseLong(parts[6]);
                user.setSubscriptionExpiryDate(new Date(expiryTime));
            } catch (NumberFormatException e) {

            }
            return user;
        }
        return null;
    }
}