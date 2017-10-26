using DAL.SQLite;

namespace MAPme.Models
{
    // ported from HelpLiterals.java
    // scripted in ?
    public class Help
    {
        [PrimaryKey, AutoIncrement]
        public int Id { get; private set; }
        [NotNull]
        public string NewRecordHelp { get; private set; }
        [NotNull]
        public string LoginHelp { get; private set; }
        [NotNull]
        public string ProfileHelp { get; private set; }
        [NotNull]
        public string HistoryHelp { get; private set; }
    }
}
