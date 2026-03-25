# Semenar  
  
#include <iostream>  
using namespace std;  
  
class AccountHolder {  
    char* firstName;  
    char* lastName;  
public:  
    AccountHolder() {  
        firstName = NULL;  
        lastName = NULL;  
    }  
};  
  
class Account {  
    int number;  
    int money;  
    AccountHolder* holder;  
public:  
    Account(int number, float money) {  
        this->number = number;  
        this->money = money;  
    }  
  
    bool transferFromAccountTo(Account* pTo, float money) {  
        if (this->getCash(money)) {  
            pTo->putMoney(money);  
            return true;  
        }  
        return false;  
    }  
  
    bool getCash(float money) {  
        if (money < this->money) {  
            this->money -= money;  
            return true;  
        }  
        return false;  
    }  
};  
