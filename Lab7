#include <iostream>
#include <iomanip>
#include <fstream>
#include <string>
#include <stdexcept>
#include <limits>
#include <vector>
#include <algorithm>

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

    bool operator==(const fio& other) const {
        return surname == other.surname && name == other.name;
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

    fio getFio() const { return man; }
    int getDepartment() const { return department; }
    double getSalary() const { return salary; }

    void setSalary(double s) {
        if (s <= 0)
            throw invalid_argument("Error: salary must be greater than 0");

        salary = s;
    }

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

void printHeader() {
    cout << left << setw(15) << "Surname"
         << setw(15) << "Name"
         << setw(12) << "Department"
         << setw(12) << "Salary" << endl;

    cout << string(54, '-') << endl;
}

void printTable(const vector<worker>& base) {
    if (base.empty()) {
        cout << "No data.\n";
        return;
    }

    printHeader();

    for_each(base.begin(), base.end(), [](const worker& w) {
        cout << w << endl;
    });
}

void addWorker(vector<worker>& base) {
    worker w;
    cin >> w;

    base.push_back(w);
}

void findWorkerByFio(const vector<worker>& base) {
    fio f;

    cout << "Enter surname and name for search:\n";
    cin >> f;

    auto it = find_if(base.begin(), base.end(), [&f](const worker& w) {
        return w == f;
    });

    if (it == base.end()) {
        cout << "Worker not found.\n";
        return;
    }

    printHeader();

    while (it != base.end()) {
        cout << *it << endl;

        it = find_if(++it, base.end(), [&f](const worker& w) {
            return w == f;
        });
    }
}

vector<worker> findByDepartment(const vector<worker>& base, int dep) {
    vector<worker> Rez;

    for_each(base.begin(), base.end(), [&Rez, dep](const worker& w) {
        if (w.getDepartment() == dep)
            Rez.push_back(w);
    });

    return Rez;
}

void increaseSalary(vector<worker>& base) {
    double percent;

    cout << "Enter percent: ";
    cin >> percent;

    if (!cin || percent <= 0) {
        cin.clear();
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
        throw invalid_argument("Error: wrong percent");
    }

    vector<worker> temp;

    transform(base.begin(), base.end(), back_inserter(temp), [percent](worker w) {
        w.setSalary(w.getSalary() + w.getSalary() * percent / 100.0);
        return w;
    });

    base = temp;
}

void saveToFile(const vector<worker>& base, const string& filename) {
    ofstream fout(filename);

    if (!fout)
        throw runtime_error("Error: cannot open file for writing");

    fout << base.size() << endl;

    for_each(base.begin(), base.end(), [&fout](const worker& w) {
        w.save(fout);
    });

    fout.close();
}

void loadFromFile(vector<worker>& base, const string& filename) {
    ifstream fin(filename);

    if (!fin)
        throw runtime_error("Error: cannot open file for reading");

    int size;

    fin >> size;

    if (!fin || size < 0)
        throw runtime_error("Error: wrong size in file");

    vector<worker> temp;

    for (int i = 0; i < size; i++) {
        worker w;
        w.load(fin);
        temp.push_back(w);
    }

    base = temp;

    fin.close();
}

void menu() {
    cout << "\nDatabase: Worker (Salary)\n";
    cout << "1. Add worker\n";
    cout << "2. Print all workers\n";
    cout << "3. Find worker by surname and name\n";
    cout << "4. Find workers by department\n";
    cout << "5. Increase salary by percent\n";
    cout << "6. Save data to file\n";
    cout << "7. Load data from file\n";
    cout << "8. Exit\n";
    cout << "Enter menu number: ";
}

int main() {
    vector<worker> base;
    vector<worker> Rez;

    int choice = 0;

    const string filename = "workers.txt";

    try {
        loadFromFile(base, filename);
        cout << "Data loaded from file.\n";
    }
    catch (...) {
        cout << "File not found. Empty database started.\n";
    }

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
                addWorker(base);
                cout << "Worker added.\n";
                break;

            case 2:
                printTable(base);
                break;

            case 3:
                findWorkerByFio(base);
                break;

            case 4: {
                int dep;

                cout << "Enter department number: ";
                cin >> dep;

                if (!cin || dep <= 0) {
                    cin.clear();
                    cin.ignore(numeric_limits<streamsize>::max(), '\n');
                    throw invalid_argument("Error: wrong department number");
                }

                Rez = findByDepartment(base, dep);

                if (Rez.empty()) {
                    cout << "Workers not found.\n";
                }
                else {
                    cout << "Search results:\n";
                    printTable(Rez);
                }

                break;
            }

            case 5:
                increaseSalary(base);
                cout << "Salary changed.\n";
                break;

            case 6:
                saveToFile(base, filename);
                cout << "Data saved to file.\n";
                break;

            case 7:
                loadFromFile(base, filename);
                cout << "Data loaded from file.\n";
                break;

            case 8:
                saveToFile(base, filename);
                cout << "Data saved. Exit program.\n";
                break;

            default:
                cout << "Wrong menu number.\n";
            }
        }
        catch (const exception& e) {
            cout << e.what() << endl;
        }

    } while (choice != 8);

    return 0;
}
