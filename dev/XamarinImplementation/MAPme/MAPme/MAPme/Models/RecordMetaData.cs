using DAL.SQLite;

namespace MAPme.Models
{
    public class RecordMetaData
    {
        [AutoIncrement, PrimaryKey]
        public int Id { get; set; }
        [NotNull]
        public bool RecordUploaded { get; set; }

        // still going to use soft deletes?
        [NotNull]
        public bool RecordDeleted { get; set; }
    }
}
