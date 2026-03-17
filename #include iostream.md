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
  
int main()  
{  
    int n;  
    cin >> n;  
  
    letter* arr = new letter[n + 1];  
  
    for (int i = 0; i < n; i++)  
        cin << arr[i];  
  
    cin << arr[n];  
    n++;  
  
    man sender;  
    cin >> sender;  
  
    letter* Rez = new letter[n];  
    int k = 0;  
  
    for (int i = 0; i < n; i++)  
        if (arr[i] == sender)  
            Rez[k++] = arr[i];  
  
    cout << left  
        << setw(15) << "adr1"  
        << setw(12) << "fam1"  
        << setw(12) << "name1"  
        << setw(15) << "adr2"  
        << setw(12) << "fam2"  
        << setw(12) << "name2"  
        << setw(10) << "cost"  
        << endl;  
  
    for (int i = 0; i < k; i++)  
        Rez[i].show();  
  
    delete[] arr;  
    delete[] Rez;  
  
    return 0;  
}  
