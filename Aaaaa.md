# Aaaaa  
  
#define _CRT_SECURE_NO_WARNINGS  
  
#include <iostream>  
#include <cstring>  
  
using namespace std;  
  
class AccountHolder {  
    char* firstName;  
    char* lastName;  
public:  
    // по умолчанию  
    AccountHolder() {  
        firstName = NULL;  
        lastName = NULL;  
    }  
  
    // с параметрами  
    AccountHolder(char* firstName, char* lastName) {  
        this->firstName = firstName;  
        this->lastName = lastName;  
    }  
  
    // копирования  
    AccountHolder(AccountHolder& holder) {  
        firstName = new char[strlen(holder.firstName) + 1];  
        strcpy(firstName, holder.firstName);  
        firstName = new char[strlen(holder.firstName) + 1];  
        strcpy(firstName, holder.firstName);  
    }  
  
    ~AccountHolder() {  
        delete[] firstName;  
        delete[] lastName;  
    }  
  
    char* getFirstName() {  
        return this->firstName;  
    }  
  
    char* getLastName() {  
        return this->lastName;  
    }  
  
    void setFirstName(char* firstName) {  
        this->firstName = firstName;  
    }  
  
    void setLastName(char* lastName) {  
        this->lastName = lastName;  
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
        this->holder.firstName = ;  
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
  
    bool putMoney(float money) {  
        if (money > 0) {  
            this->money += money;  
        }  
    }  
  
    float getMoney() {  
        return this->money;  
    }  
  
    void setMoney(float money) {  
        this->money = money;  
    }  
  
    int getNumber() {  
        return this->number;  
    }  
  
    void setNumber(int number) {  
        this->number = number;  
    }  
};  
  
int main()  
{  
  
}  
