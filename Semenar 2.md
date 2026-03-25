# Semenar 2  
  
#define _CRT_SECURE_NO_WARNINGS  
#include <iostream>  
#include <cstring>  
  
using namespace std;  
  
class AccountHolder {  
    char* firstName;  
    char* lastName;  
  
public:  
    // constructor by default  
    AccountHolder() {  
        firstName = NULL;  
        lastName = NULL;  
    }  
  
    // constructor with parameters  
    AccountHolder(const char* firstName, const char* lastName) {  
        this->firstName = new char[strlen(firstName) + 1];  
        strcpy(this->firstName, firstName);  
  
        this->lastName = new char[strlen(lastName) + 1];  
        strcpy(this->lastName, lastName);  
    }  
  
    // copy constructor  
    AccountHolder(const AccountHolder& holder) {  
        if (holder.firstName != NULL) {  
            firstName = new char[strlen(holder.firstName) + 1];  
            strcpy(firstName, holder.firstName);  
        }  
        else {  
            firstName = NULL;  
        }  
  
        if (holder.lastName != NULL) {  
            lastName = new char[strlen(holder.lastName) + 1];  
            strcpy(lastName, holder.lastName);  
        }  
        else {  
            lastName = NULL;  
        }  
    }  
  
    // operator =  
    AccountHolder& operator=(const AccountHolder& holder) {  
        if (this == &holder)  
            return *this;  
  
        delete[] firstName;  
        delete[] lastName;  
  
        if (holder.firstName != NULL) {  
            firstName = new char[strlen(holder.firstName) + 1];  
            strcpy(firstName, holder.firstName);  
        }  
        else {  
            firstName = NULL;  
        }  
  
        if (holder.lastName != NULL) {  
            lastName = new char[strlen(holder.lastName) + 1];  
            strcpy(lastName, holder.lastName);  
        }  
        else {  
            lastName = NULL;  
        }  
  
        return *this;  
    }  
  
    // destructor  
    ~AccountHolder() {  
        delete[] firstName;  
        delete[] lastName;  
    }  
  
    char* getFirstName() const {  
        return firstName;  
    }  
  
    char* getLastName() const {  
        return lastName;  
    }  
  
    void setFirstName(const char* firstName) {  
        delete[] this->firstName;  
        this->firstName = new char[strlen(firstName) + 1];  
        strcpy(this->firstName, firstName);  
    }  
  
    void setLastName(const char* lastName) {  
        delete[] this->lastName;  
        this->lastName = new char[strlen(lastName) + 1];  
        strcpy(this->lastName, lastName);  
    }  
  
    void print() const {  
        if (firstName != NULL && lastName != NULL)  
            cout << firstName << " " << lastName << endl;  
        else  
            cout << "EMPTY" << endl;  
    }  
};  
  
class Account {  
    int number;  
    float money;  
    AccountHolder holder;  
  
public:  
    // default constructor  
    Account() {  
        number = 0;  
        money = 0;  
    }  
  
    // constructor with parameters  
    Account(int number, float money, const AccountHolder& holder) : holder(holder) {  
        this->number = number;  
        this->money = money;  
    }  
  
    // copy constructor  
    Account(const Account& other) : holder(other.holder) {  
        number = other.number;  
        money = other.money;  
    }  
  
    // operator =  
    Account& operator=(const Account& other) {  
        if (this == &other)  
            return *this;  
  
        number = other.number;  
        money = other.money;  
        holder = other.holder;  
  
        return *this;  
    }  
  
    // destructor  
    ~Account() {  
    }  
  
    bool getCash(float money) {  
        if (money > 0 && money <= this->money) {  
            this->money -= money;  
            return true;  
        }  
        return false;  
    }  
  
    bool putMoney(float money) {  
        if (money > 0) {  
            this->money += money;  
            return true;  
        }  
        return false;  
    }  
  
    bool transferFromAccountTo(Account* pTo, float money) {  
        if (this->getCash(money)) {  
            pTo->putMoney(money);  
            return true;  
        }  
        return false;  
    }  
  
    int getNumber() const {  
        return number;  
    }  
  
    float getMoney() const {  
        return money;  
    }  
  
    void setNumber(int number) {  
        this->number = number;  
    }  
  
    void setMoney(float money) {  
        this->money = money;  
    }  
  
    void print() const {  
        cout << "Number: " << number << endl;  
        cout << "Money: " << money << endl;  
        cout << "Holder: ";  
        holder.print();  
        cout << endl;  
    }  
};  
  
int main() {  
    AccountHolder holder1("Ivan", "Ivanov");  
    cout << "holder1:" << endl;  
    holder1.print();  
    cout << endl;  
  
    AccountHolder holder2(holder1);  
    cout << "holder2 (copy constructor):" << endl;  
    holder2.print();  
    cout << endl;  
  
    AccountHolder holder3 = holder1;  
    cout << "holder3 (= at initialization):" << endl;  
    holder3.print();  
    cout << endl;  
  
    AccountHolder holder4;  
    holder4 = holder1;  
    cout << "holder4 (operator=):" << endl;  
    holder4.print();  
    cout << endl;  
  
    AccountHolder* pHolder = new AccountHolder("Ali", "Ahmad");  
    cout << "pHolder:" << endl;  
    pHolder->print();  
    cout << endl;  
  
    AccountHolder* arr = new AccountHolder[3];  
    delete[] arr;  
  
    Account acc1(1, 1000, holder1);  
    Account acc2(2, 2000, holder2);  
  
    cout << "Before transfer:" << endl;  
    acc1.print();  
    acc2.print();  
  
    if (acc1.transferFromAccountTo(&acc2, 300)) {  
        cout << "Transfer completed" << endl;  
    }  
    else {  
        cout << "Transfer failed" << endl;  
    }  
  
    cout << endl;  
    cout << "After transfer:" << endl;  
    acc1.print();  
    acc2.print();  
  
    delete pHolder;  
  
    return 0;  
}  
