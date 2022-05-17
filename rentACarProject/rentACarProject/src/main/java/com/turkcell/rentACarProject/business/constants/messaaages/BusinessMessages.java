package com.turkcell.rentACarProject.business.constants.messaaages;

public class BusinessMessages {


    public class GlobalMessages{

        public static final String DATA_LISTED_SUCCESSFULLY = "Data Listed Successfully";
        public static final String DATA_ADDED_SUCCESSFULLY = "Data Added Successfully";
        public static final String DATA_UPDATED_SUCCESSFULLY = "Data updated Successfully, dataId: ";
        public static final String DATA_DELETED_SUCCESSFULLY = "Data deleted successfully, dataId: ";
        public static final String DATA_BROUGHT_SUCCESSFULLY = "Data Brought Successfully, dataId: ";
    }


    public class AdditionalMessages{

        public static final String ADDITIONAL_ID_NOT_FOUND = "Additional Service ID not exists, additionalId: ";
        public static final String ADDITIONAL_NAME_ALREADY_EXISTS = "Additional Service name already exists, name: ";
    }

    public class BrandMessages{

        public static final String BRAND_ID_NOT_FOUND = "Brand ID not exists, brandId: ";
        public static final String BRAND_NAME_ALREADY_EXISTS = "Brand name already exists, name: ";
    }

    public class CarCrashMessages{

        public static final String CAR_CRASH_LISTED_BY_CAR_ID = "Car Crash listed successfully by car ID, carId: ";
        public static final String CAR_CRASH_ID_NOT_FOUND = "Car Crash ID not exists, carCrashId: ";
        public static final String CRASH_DATE_CANNOT_AFTER_TODAY = "Crash date cannot be after today, crashDate: ";
        public static final String CAR_ID_ALREADY_EXISTS_IN_THE_CAR_CRASH_TABLE = "Car ID already exists in the Car Crash table, carId: ";
    }

    public class CarMaintenanceMessages{

        public static final String CAR_MAINTENANCE_LISTED_BY_CAR_ID = "Car Maintenances are listed successfully by car ID, carId: ";
        public static final String RETURN_DATE_CANNOT_BEFORE_TODAY = "Return date cannot be a past date, returnDate: ";
        public static final String CAR_ALREADY_IN_MAINTENANCE = "Car in maintenance on the entered dates";
        public static final String CAR_MAINTENANCE_ID_NOT_FOUND = "Car Maintenance ID not exists, carMaintenanceId: ";
        public static final String CAR_ID_ALREADY_EXISTS_IN_THE_CAR_MAINTENANCE_TABLE = "Car ID already exists in the Car Maintenance table, carId: ";
    }

    public class CarMessages{

        public static final String CAR_LISTED_BY_BRAND_ID = "Car listed successfully by brand ID, brandId: ";
        public static final String CAR_LISTED_BY_COLOR_ID = "Car listed successfully by color ID, colorId: ";
        public static final String CAR_LISTED_BY_LESS_THEN_EQUAL = "Car listed successfully by Daily Price less then equal, dailyPrice: ";
        public static final String ALL_CARS_PAGED = "All cars paged successfully";
        public static final String ALL_CARS_SORTED = "All cars sorted successfully";
        public static final String MODEL_YEAR_CANNOT_AFTER_TODAY = "The model year may be this year or previous years, modelYear: ";
        public static final String DELIVERED_KILOMETER_CANNOT_LESS_THAN_RENTED_KILOMETER = "Delivered kilometer cannot be lower than rented kilometer";
        public static final String CAR_ID_NOT_FOUND = "Car ID not exists, carId: ";
        public static final String BRAND_ID_ALREADY_EXISTS_IN_THE_CAR_TABLE = "Brand ID already exists in the Car table, brandId: ";
        public static final String COLOR_ID_ALREADY_EXISTS_IN_THE_CAR_TABLE = "Color ID already exists in the Car table, colorId: ";
        public static final String PAGE_NO_OR_PAGE_SIZE_NOT_VALID = "Page No or Page Size value not valid.";
    }

    public class CityMessages{

        public static final String CITY_ID_NOT_FOUND = "City ID not exists, cityId: ";
        public static final String CITY_NAME_ALREADY_EXISTS = "City name already exists, cityName: ";
    }

    public class ColorMessages{

        public static final String COLOR_ID_NOT_FOUND = "Color ID not exists, colorId: ";
        public static final String COLOR_NAME_ALREADY_EXISTS = "Color name already exists, colorName: ";
    }

    public class CorporateCustomerMessages{

        public static final String CORPORATE_CUSTOMER_ID_NOT_FOUND = "Corporate Customer ID not exists, corporateCustomerId: ";
        public static final String TAX_NAME_ALREADY_EXISTS = "Tax Number already exists, taxNumber: ";
        public static final String TAX_NUMBER_NOT_VALID = "Tax number not valid, must be only 10 digits and must be only numbers";
    }


    public class CreditCardMessages{

        public static final String CREDIT_CARD_LISTED_BY_CUSTOMER_ID = "Credit card listed successfully by customer ID, customerId: ";
        public static final String CREDIT_CARD_ID_NOT_FOUND = "Credit card ID not exists, creditCardId: ";
        public static final String CREDIT_CARD_ALREADY_EXISTS = "Credit card number already exists, creditCardId: ";
        public static final String CREDIT_CARD_CARD_NUMBER_NOT_VALID = "Credit card Card Number not Valid, must be only 16 digits and must be only number";
        public static final String CREDIT_CARD_CVV_NOT_VALID = "Credit card CVV not Valid, must be only 3 digits and must be only number";
        public static final String CREDIT_CARD_OWNER_NOT_VALID = "Credit card Owner not Valid, must be only 5-50 digits and must be only String";

    }

    public class CustomerMessages{

        public static final String CUSTOMER_ID_NOT_FOUND = "Customer ID not exists, customerId: ";
    }

    public class IndividualCustomerMessages{

        public static final String INDIVIDUAL_CUSTOMER_ID_NOT_FOUND = "Individual Customer ID not exists, individualCustomerId: ";
        public static final String NATIONAL_IDENTITY_ALREADY_EXISTS = "National Identity already exists, nationalIdentity: ";
        public static final String NATIONAL_IDENTITY_NOT_VALID = "National Identity not valid, must be only 11 digits and must be only number";
    }

    public class InvoiceMessages{

        public static final String INDIVIDUAL_CUSTOMER_INVOICE_LISTED_BY_INVOICE_ID = "Individual Customer Invoice listed successfully by individual invoice id, invoiceId: ";
        public static final String CORPORATE_CUSTOMER_INVOICE_LISTED_BY_INVOICE_ID = "Corporate Customer Invoice listed successfully by corporate invoice id, invoiceId: ";
        public static final String INDIVIDUAL_CUSTOMER_INVOICE_LISTED_BY_INVOICE_NO = "Individual Customer Invoice listed successfully by individual invoice no, invoiceNo: ";
        public static final String CORPORATE_CUSTOMER_INVOICE_LISTED_BY_INVOICE_NO = "Corporate Customer Invoice listed successfully by corporate invoice no, invoiceNo: ";
        public static final String INVOICE_LISTED_BY_PAYMENT_ID = "Invoice listed successfully  by payment ID, paymentId: ";
        public static final String INVOICE_LISTED_BY_RENTAL_ID = "Invoice listed successfully  by rental car ID, rentalCarId: ";
        public static final String INVOICE_LISTED_BY_CUSTOMER_ID = "Invoice listed successfully  by customer ID, customerId: ";
        public static final String INVOICE_LISTED_BY_BETWEEN_DATE = "Invoices listed successfully by between the entered dates";
        public static final String INVOICE_ID_NOT_FOUND = "Invoice ID not exists, invoiceId: ";
        public static final String INVOICE_NO_NOT_FOUND = "Invoice NO not exists, invoiceNo: ";
        public static final String PAYMENT_ID_NOT_FOUND_IN_THE_INVOICE_TABLE = "Payment ID not found in the Invoice table, paymentId: ";
        public static final String CUSTOMER_ID_ALREADY_EXISTS_IN_THE_INVOICE_TABLE = "Customer ID already exists in the Invoice table, customerId: ";
        public static final String RENTAL_CAR_ID_ALREADY_EXISTS_IN_THE_INVOICE_TABLE = "Rental Car ID already exists in the Invoice table, rentalCarId: ";
        public static final String INVOICE_NO_NOT_VALID = "Invoice no not valid";
    }

    public class OrderedAdditionalMessages{

        public static final String ORDERED_ADDITIONAL_LISTED_BY_RENTAL_CAR_ID = "Ordered Additional listed successfully by rental car ID, rentalCarId: ";
        public static final String ORDERED_ADDITIONAL_LISTED_BY_ADDITIONAL_ID = "Ordered Additional listed successfully by additional ID, additionalId: ";
        public static final String ADDITIONAL_QUANTITY_NOT_VALID = "For this additional service, the Minimum quantity can be 1, the maximum quantity is: ";
        public static final String ORDERED_ADDITIONAL_ALREADY_EXISTS = "Ordered Additional already exists";
        public static final String ORDERED_ADDITIONAL_ID_NOT_FOUND = "Ordered Additional ID not exists, orderedAdditionalId: ";
        public static final String RENTAL_CAR_ID_ALREADY_EXISTS_IN_THE_ORDERED_ADDITIONAL_TABLE = "Rental Car ID already exists in the Ordered Additional table, rentalCarId: ";
        public static final String ADDITIONAL_ID_ALREADY_EXISTS_IN_THE_ORDERED_ADDITIONAL_TABLE = "Additional ID already exists in the Ordered Additional table, additionalId: ";
    }

    public class PaymentMessages{

        public static final String PAYMENT_AND_RENT_CAR_SUCCESSFULLY = "Payment, Car Rental, Additional Service adding and Invoice creation successfully.";
        public static final String PAYMENT_AND_RENT_CAR_UPDATE_SUCCESSFULLY = "Payment, Car Rental update and Invoice creation successfully.";
        public static final String PAYMENT_AND_RENT_DELIVERY_DATE_UPDATE_SUCCESSFULLY = "Payment, Car Rental update and Invoice creation successfully.";
        public static final String PAYMENT_AND_ADDITIONAL_SERVICE_ADDING_SUCCESSFULLY = "Payment, Additional Service adding and Invoice creation successfully.";
        public static final String PAYMENT_AND_ADDITIONAL_SERVICE_UPDATE_SUCCESSFULLY = "Payment, Additional Service update and Invoice creation successfully.";
        public static final String PAYMENT_LISTED_BY_RENTAL_CAR_ID = "Payment listed successfully by rental carID, rentalCarId: ";
        public static final String PAYMENT_ID_NOT_FOUND = "Payment ID not exists, paymentId: ";
        public static final String RENTAL_CAR_ID_ALREADY_EXISTS_IN_THE_PAYMENT_TABLE = "Rental Car ID already exists in the Payment table, rentalCarId: ";
    }

    public class RentalCarMessages{

        public static final String CAR_DELIVERED = "The car was delivere, rentalCarId: ";
        public static final String CAR_RECEIVED = "The car received, rentalCarId: ";
        public static final String RENTAL_CAR_LISTED_BY_CAR_ID = "Rental Car listed successfully by car ID, carId: ";
        public static final String RENTAL_CAR_LISTED_BY_RENTED_CITY_ID = "Rental Car listed successfully by rented city ID, rentedCityId: ";
        public static final String RENTAL_CAR_LISTED_BY_DELIVERED_CITY_ID = "Rental Car listed successfully by delivered city ID, deliveredCityId: ";
        public static final String RENTAL_CAR_LISTED_BY_CUSTOMER_ID = "Rental Car listed successfully by customer ID, customerId: ";
        public static final String RENTAL_CAR_LISTED_BY_INDIVIDUAL_CUSTOMER_ID = "Rental Car listed successfully by individual customer ID, individualCustomerId: ";
        public static final String RENTAL_CAR_LISTED_BY_CORPORATE_CUSTOMER_ID = "Rental Car listed successfully by corporate customer ID, corporateCustomerId: ";
        public static final String START_DATE_CANNOT_BEFORE_TODAY = "Start date cannot be earlier than today, startDate: ";
        public static final String FINISH_DATE_CANNOT_BEFORE_START_DATE = "Finish date cannot be earlier than start date";
        public static final String SECOND_DATE_CANNOT_BEFORE_FIRST_DATE = "Second date cannot be earlier than first date";
        public static final String CAR_ALREADY_RENTED_ENTERED_DATES = "The car already rented entered date";
        public static final String CAR_IN_MAINTENANCE_ENTERED_DATES = "The car in maintenance entered date";
        public static final String RENTED_KILOMETER_ALREADY_EXISTS = "The rented kilometer is already exists, the car has already been rented";
        public static final String RENTED_KILOMETER_NULL = "The car has not yet been delivered to the customer, the rented kilometer cannot be null";
        public static final String DELIVERED_KILOMETER_ALREADY_EXISTS = "The delivered kilometer is already exists, the car has already been delivered";
        public static final String RENTAL_CAR_ID_NOT_FOUND = "Rental Car ID not exists, rentalCarId: ";
        public static final String CAR_ID_ALREADY_EXISTS_IN_THE_RENTAL_CAR_TABLE = "Car ID already exists in the Rental Car table, carId: ";
        public static final String CUSTOMER_ID_ALREADY_EXISTS_IN_THE_RENTAL_CAR_TABLE = "Customer ID already exists in the Rental Car table, customerId: ";
        public static final String RENTAL_CAR_ID_OR_CAR_ID_NOT_FOUND = "Rental Car ID or Car ID not exists.";
    }

    public class UserMessages{

        public static final String USER_ID_NOT_FOUND = "User ID not exists, userId: ";
        public static final String USER_EMAIL_ALREAY_EXISTS = "User Email already exists, email: ";
    }
}
