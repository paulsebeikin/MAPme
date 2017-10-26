using DAL.SQLite;
using System;
using System.Collections.Generic;
using System.Text;

namespace MAPme.DAL.Interfaces
{
    public interface IDalInterface
    {
        string GetDatabasePath(string location);
        SQLiteConnection GetSQLiteConnection(string databasePath);  // synchronous DB connection

        void CreateTable<T>();
        void Insert<T>(T table);
        IEnumerable<T> Query <T>(SQLiteConnection db, T table, string sql);
        void Update<T>(SQLiteConnection db, T table, string sql);
        void Delete<T>(SQLiteConnection db, T table, string sql);
    }
}
