using MAPme.DAL.Interfaces;
using System;
using System.Collections.Generic;
using System.Text;
using DAL.SQLite;

namespace MAPme.DAL
{
    public class DalManager : IDalInterface
    {
        public void CreateTable<T>()
        {
            throw new NotImplementedException();
        }

        public void Delete<T>(SQLiteConnection db, T table, string sql)
        {
            throw new NotImplementedException();
        }

        public string GetDatabasePath(string location)
        {
            throw new NotImplementedException();
        }

        public SQLiteConnection GetSQLiteConnection(string databasePath)
        {
            throw new NotImplementedException();
        }

        public void Insert<T>(T table)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<T> Query<T>(SQLiteConnection db, T table, string sql)
        {
            throw new NotImplementedException();
        }

        public void Update<T>(SQLiteConnection db, T table, string sql)
        {
            throw new NotImplementedException();
        }
    }
}
S