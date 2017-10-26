using DAL.SQLite;
using System;
using System.Collections.Generic;
using System.Text;

namespace MAPme.DAL.Interfaces
{
    public interface IDalInterface
    {
        void CreateTable<T>(T table);
        void Insert<T>(T table);
        IEnumerable<T> Query <T>(string sql, params object[] args);
        void Update<T>(T table);
        void Delete<T>(T table);
        void DeleteByPrimaryKey<T>(int primaryKey);
    }
}
