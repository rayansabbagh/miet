#include <iostream>
#include <iomanip>
#include <fstream>
#include <string>
#include <stdexcept>
#include <limits>

using namespace std;

class fio {
private:
    string surname;
    string name;

public:
    fio() : surname(""), name("") {}

    fio(string s, string n) : surname(s), name(n) {
        if (surname.empty() || name.empty())
            throw invalid_argument("Error: empty surname or name");
    }

    string getSurname() const { return surname; }
    string getName() const { return name; }

    void setSurname(string s) {
        if (s.empty())
            throw invalid_argument("Error: empty surname");
        surname = s;
    }

    void setName(string n) {
        if (n.empty())
            throw invalid_argument("Error: empty name");
        name = n;
    }

    friend istream& operator>>(istream& in, fio& f) {
        cout << "Surname: ";
        in >> f.surname;

        cout << "Name: ";
        in >> f.name;

        if (!in || f.surname.empty() || f.name.empty())
            throw invalid_argument("Error: wrong surname or name");

        return in;
    }

    friend ostream& operator<<(ostream& out, const fio& f) {
        out << left << setw(15) << f.surname
            << setw(15) << f.name;
        return out;
    }

    bool operator==(const fio& other) const {
        return surname == other.surname && name == other.name;
    }
};

class worker {
private:
    fio man;
    int department;
    double salary;

public:
    worker() : department(0), salary(0) {}

    worker(fio m, int d, double s) : man(m), department(d), salary(s) {
        if (department <= 0)
            throw invalid_argument("Error: department must be greater than 0");

        if (salary <= 0)
            throw invalid_argument("Error: salary must be greater than 0");
    }

    int getDepartment() const { return department; }
    double getSalary() const { return salary; }
    fio getFio() const { return man; }

    bool operator==(const fio& f) const {
        return man == f;
    }

    friend istream& operator>>(istream& in, worker& w) {
        cout << "Enter worker data:\n";

        in >> w.man;

        cout << "Department: ";
        in >> w.department;

        if (!in || w.department <= 0) {
            in.clear();
            in.ignore(numeric_limits<streamsize>::max(), '\n');
            throw invalid_argument("Error: wrong department number");
        }

        cout << "Salary: ";
        in >> w.salary;

        if (!in || w.salary <= 0) {
            in.clear();
            in.ignore(numeric_limits<streamsize>::max(), '\n');
            throw invalid_argument("Error: wrong salary");
        }

        return in;
    }

    friend ostream& operator<<(ostream& out, const worker& w) {
        out << w.man
            << left << setw(12) << w.department
            << fixed << setprecision(2) << setw(12) << w.salary;
        return out;
    }

    void save(ofstream& fout) const {
        fout << man.getSurname() << " "
             << man.getName() << " "
             << department << " "
             << salary << endl;
    }

    void load(ifstream& fin) {
        string s, n;

        fin >> s >> n >> department >> salary;

        if (!fin)
            throw runtime_error("Error: file reading error");

        man.setSurname(s);
        man.setName(n);

        if (department <= 0 || salary <= 0)
            throw invalid_argument("Error: wrong data in file");
    }
};

void printTable(worker* arr, int n) {
    if (n == 0) {
        cout << "No data.\n";
        return;
    }

    cout << left << setw(15) << "Surname"
         << setw(15) << "Name"
         << setw(12) << "Department"
         << setw(12) << "Salary" << endl;

    cout << string(54, '-') << endl;

    for (int i = 0; i < n; i++) {
        cout << arr[i] << endl;
    }
}

worker* findByDepartment(worker* arr, int n, int dep, int& count) {
    count = 0;

    for (int i = 0; i < n; i++) {
        if (arr[i].getDepartment() == dep)
            count++;
    }

    if (count == 0)
        return nullptr;

    worker* Rez = new worker[count];

    int j = 0;

    for (int i = 0; i < n; i++) {
        if (arr[i].getDepartment() == dep) {
            Rez[j] = arr[i];
            j++;
        }
    }

    return Rez;
}

void saveToFile(worker* arr, int n, const string& filename) {
    ofstream fout(filename);

    if (!fout)
        throw runtime_error("Error: cannot open file for writing");

    fout << n << endl;

    for (int i = 0; i < n; i++) {
        arr[i].save(fout);
    }

    fout.close();
}

void loadFromFile(worker*& arr, int& n, const string& filename) {
    ifstream fin(filename);

    if (!fin)
        throw runtime_error("Error: cannot open file for reading");

    int size;
    fin >> size;

    if (!fin || size < 0)
        throw runtime_error("Error: wrong array size in file");

    worker* temp = new worker[size];

    for (int i = 0; i < size; i++) {
        temp[i].load(fin);
    }

    delete[] arr;

    arr = temp;
    n = size;

    fin.close();
}

void addWorker(worker*& arr, int& n) {
    worker w;
    cin >> w;

    worker* temp = new worker[n + 1];

    for (int i = 0; i < n; i++) {
        temp[i] = arr[i];
    }

    temp[n] = w;

    delete[] arr;

    arr = temp;
    n++;
}

void findWorkerByFio(worker* arr, int n) {
    fio f;

    cout << "Enter surname and name for search:\n";
    cin >> f;

    bool found = false;

    cout << left << setw(15) << "Surname"
         << setw(15) << "Name"
         << setw(12) << "Department"
         << setw(12) << "Salary" << endl;

    cout << string(54, '-') << endl;

    for (int i = 0; i < n; i++) {
        if (arr[i] == f) {
            cout << arr[i] << endl;
            found = true;
        }
    }

    if (!found)
        cout << "Worker not found.\n";
}

void menu() {
    cout << "\nDatabase: Worker (Salary)\n";
    cout << "1. Add worker\n";
    cout << "2. Print all workers\n";
    cout << "3. Find worker by surname and name\n";
    cout << "4. Find workers by department\n";
    cout << "5. Save data to file\n";
    cout << "6. Load data from file\n";
    cout << "7. Exit\n";
    cout << "Enter menu number: ";
}

int main() {
    worker* base = nullptr;
    worker* Rez = nullptr;

    int n = 0;
    int choice = 0;

    const string filename = "workers.txt";

    do {
        try {
            menu();
            cin >> choice;

            if (!cin) {
                cin.clear();
                cin.ignore(numeric_limits<streamsize>::max(), '\n');
                throw invalid_argument("Error: enter menu number");
            }

            switch (choice) {
            case 1:
                addWorker(base, n);
                cout << "Worker added.\n";
                break;

            case 2:
                printTable(base, n);
                break;

            case 3:
                findWorkerByFio(base, n);
                break;

            case 4: {
                int dep;
                int count;

                cout << "Enter department number: ";
                cin >> dep;

                if (!cin || dep <= 0) {
                    cin.clear();
                    cin.ignore(numeric_limits<streamsize>::max(), '\n');
                    throw invalid_argument("Error: wrong department number");
                }

                delete[] Rez;
                Rez = findByDepartment(base, n, dep, count);

                if (Rez == nullptr) {
                    cout << "Workers not found.\n";
                } else {
                    cout << "Search results:\n";
                    printTable(Rez, count);
                }

                break;
            }

            case 5:
                saveToFile(base, n, filename);
                cout << "Data saved to file.\n";
                break;

            case 6:
                loadFromFile(base, n, filename);
                cout << "Data loaded from file.\n";
                break;

            case 7:
                cout << "Exit program.\n";
                break;

            default:
                cout << "Wrong menu number.\n";
            }
        }
        catch (const exception& e) {
            cout << e.what() << endl;
        }

    } while (choice != 7);

    delete[] base;
    delete[] Rez;

    return 0;
}
