using MAPme.DAL.Interfaces;
using System;
using System.Collections.Generic;
using DAL.SQLite;
using System.IO;
using System.Linq.Expressions;
using System.Linq;

namespace MAPme.DAL
{
    public sealed class DalManager : IDalInterface
    {
        private string _databasePath
        {
            // needs some testing
            get
            {
                var dbName = "mapme.db";
                #if SILVERLIGHT
                    var path = dbName;
                #else
                    #if __ANDROID__
                        string location = Environment.GetFolderPath(Environment.SpecialFolder.Personal); ;
                    #else
                        #if __IOS__
                            string personalPath = Environment.GetFolderPath (Environment.SpecialFolder.Personal);
                            string location = Path.Combine (personalPath, "..", "Library");
                        #else
                            // UWP
                            string location = Windows.Storage.ApplicationData.Current.LocalFolder.Path;
                        #endif
                    #endif
                var path = Path.Combine(location, dbName);
                #endif

                return path;
            }
        }

        private SQLiteConnection _databaseConnection
        {
            get
            {
                return new SQLiteConnection(_databasePath);
            }
        }

        public void CreateTable<T>(T table)
        {
            if (table == null)
            {
                throw new ArgumentNullException("table");
            }

            _databaseConnection.CreateTable(typeof(T));
        }

        public void Insert<T>(T table)
        {
            if (table == null)
            {
                throw new ArgumentNullException("table");
            }

            _databaseConnection.Insert(table, typeof(T));
        }

        public void Update<T>(T table)
        {
            if (table == null)
            {
                throw new ArgumentNullException("table");
            }

            _databaseConnection.Update(table, typeof(T));
        }

        public void Delete<T>(T table)
        {
            if (table == null)
            {
                throw new ArgumentNullException("table");
            }

            _databaseConnection.Delete(table);
        }

        public void DeleteByPrimaryKey<T>(int primaryKey)
        {
            if (primaryKey <= 0)
            {
                throw new ArgumentException("Invalid primary key");
            }

            _databaseConnection.Delete(primaryKey);
        }

        public IEnumerable<T> QueryTable<T>(T table, Expression<Func<T, bool>> predicate)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<T> Query<T>(string sql, params object[] args)
        {
            throw new NotImplementedException();
        }
    }
}
