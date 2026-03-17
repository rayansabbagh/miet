#include <iostream>  
#include <iomanip>  
#include <string>  
  
using namespace std;  
  
class man  
{  
private:  
    string fam;  
    string name;  
  
public:  
    man() : fam(""), name("") {}  
  
    bool operator==(const man& other) const  
    {  
        return fam == other.fam && name == other.name;  
    }  
  
    friend istream& operator>>(istream& in, man& m)  
    {  
        in >> m.fam >> m.name;  
        return in;  
    }  
  
    string getFam() const { return fam; }  
    string getName() const { return name; }  
};  
  
class letter  
{  
private:  
    string adr1;  
    man fname1;  
    string adr2;  
    man fname2;  
    double cost;  
  
public:  
    letter() : adr1(""), fname1(), adr2(""), fname2(), cost(0) {}  
  
    bool operator==(const man& m) const  
    {  
        return fname2 == m;  
    }  
  
    friend istream& operator<<(istream& in, letter& l)  
    {  
        in >> l.adr1;  
        in >> l.fname1;  
        in >> l.adr2;  
        in >> l.fname2;  
  
        do  
        {  
            in >> l.cost;  
            if (l.cost <= 0)  
                cout << "Cost must be > 0. Enter cost again: ";  
        } while (l.cost <= 0);  
  
        return in;  
    }  
  
    void show() const  
    {  
        cout << left  
             << setw(15) << adr1  
             << setw(12) << fname1.getFam()  
             << setw(12) << fname1.getName()  
             << setw(15) << adr2  
             << setw(12) << fname2.getFam()  
             << setw(12) << fname2.getName()  
             << setw(10) << fixed << setprecision(2) << cost  
             << endl;  
    }  
};  
  
void header()  
{  
    cout << left  
         << setw(15) << "adr1"  
         << setw(12) << "fam1"  
         << setw(12) << "name1"  
         << setw(15) << "adr2"  
         << setw(12) << "fam2"  
         << setw(12) << "name2"  
         << setw(10) << "cost"  
         << endl;  
}  
  
int main()  
{  
    letter* arr = new letter[100];  
    int n = 0;  
    int choice;  
  
    do  
    {  
        cout << "\n========== MENU ==========\n";  
        cout << "1 - Add new letter\n";  
        cout << "2 - Find letters by sender\n";  
        cout << "3 - Show all letters\n";  
        cout << "0 - Exit\n";  
        cout << "Enter your choice: ";  
        cin >> choice;  
  
        if (choice == 1)  
        {  
            cout << "\nEnter letter data in this order:\n";  
            cout << "adr1 fam1 name1 adr2 fam2 name2 cost\n";  
            cout << "Example:\n";  
            cout << "moscow sabbagh rayan telaviv sabbagh amer 120\n";  
            cout << "Input: ";  
  
            cin << arr[n];  
            n++;  
  
            cout << "Letter added successfully.\n";  
        }  
        else if (choice == 2)  
        {  
            if (n == 0)  
            {  
                cout << "Database is empty.\n";  
                continue;  
            }  
  
            cout << "\nEnter sender data in this order:\n";  
            cout << "fam name\n";  
            cout << "Example:\n";  
            cout << "sabbagh amer\n";  
            cout << "Input: ";  
  
            man sender;  
            cin >> sender;  
  
            letter* Rez = new letter[n];  
            int k = 0;  
  
            for (int i = 0; i < n; i++)  
                if (arr[i] == sender)  
                    Rez[k++] = arr[i];  
  
            if (k == 0)  
            {  
                cout << "No letters found.\n";  
            }  
            else  
            {  
                cout << "\nSearch result:\n";  
                header();  
                for (int i = 0; i < k; i++)  
                    Rez[i].show();  
            }  
  
            delete[] Rez;  
        }  
        else if (choice == 3)  
        {  
            if (n == 0)  
            {  
                cout << "Database is empty.\n";  
            }  
            else  
            {  
                cout << "\nAll letters:\n";  
                header();  
                for (int i = 0; i < n; i++)  
                    arr[i].show();  
            }  
        }  
        else if (choice == 0)  
        {  
            cout << "Program finished.\n";  
        }  
        else  
        {  
            cout << "Wrong choice. Try again.\n";  
        }  
  
    } while (choice != 0);  
  
    delete[] arr;  
    return 0;  
}  
